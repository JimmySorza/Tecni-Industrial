package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.Tecnico;
import com.tecnindustrial.repository.TecnicoRepository;
import com.tecnindustrial.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tecnindustrial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TecnicoResource REST controller.
 *
 * @see TecnicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class TecnicoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTecnicoMockMvc;

    private Tecnico tecnico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TecnicoResource tecnicoResource = new TecnicoResource(tecnicoRepository);
        this.restTecnicoMockMvc = MockMvcBuilders.standaloneSetup(tecnicoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tecnico createEntity(EntityManager em) {
        Tecnico tecnico = new Tecnico()
            .nombre(DEFAULT_NOMBRE);
        return tecnico;
    }

    @Before
    public void initTest() {
        tecnico = createEntity(em);
    }

    @Test
    @Transactional
    public void createTecnico() throws Exception {
        int databaseSizeBeforeCreate = tecnicoRepository.findAll().size();

        // Create the Tecnico
        restTecnicoMockMvc.perform(post("/api/tecnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tecnico)))
            .andExpect(status().isCreated());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicoList = tecnicoRepository.findAll();
        assertThat(tecnicoList).hasSize(databaseSizeBeforeCreate + 1);
        Tecnico testTecnico = tecnicoList.get(tecnicoList.size() - 1);
        assertThat(testTecnico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTecnicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tecnicoRepository.findAll().size();

        // Create the Tecnico with an existing ID
        tecnico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTecnicoMockMvc.perform(post("/api/tecnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tecnico)))
            .andExpect(status().isBadRequest());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicoList = tecnicoRepository.findAll();
        assertThat(tecnicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTecnicos() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Get all the tecnicoList
        restTecnicoMockMvc.perform(get("/api/tecnicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tecnico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);

        // Get the tecnico
        restTecnicoMockMvc.perform(get("/api/tecnicos/{id}", tecnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tecnico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTecnico() throws Exception {
        // Get the tecnico
        restTecnicoMockMvc.perform(get("/api/tecnicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);
        int databaseSizeBeforeUpdate = tecnicoRepository.findAll().size();

        // Update the tecnico
        Tecnico updatedTecnico = tecnicoRepository.findOne(tecnico.getId());
        // Disconnect from session so that the updates on updatedTecnico are not directly saved in db
        em.detach(updatedTecnico);
        updatedTecnico
            .nombre(UPDATED_NOMBRE);

        restTecnicoMockMvc.perform(put("/api/tecnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTecnico)))
            .andExpect(status().isOk());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicoList = tecnicoRepository.findAll();
        assertThat(tecnicoList).hasSize(databaseSizeBeforeUpdate);
        Tecnico testTecnico = tecnicoList.get(tecnicoList.size() - 1);
        assertThat(testTecnico.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTecnico() throws Exception {
        int databaseSizeBeforeUpdate = tecnicoRepository.findAll().size();

        // Create the Tecnico

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTecnicoMockMvc.perform(put("/api/tecnicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tecnico)))
            .andExpect(status().isCreated());

        // Validate the Tecnico in the database
        List<Tecnico> tecnicoList = tecnicoRepository.findAll();
        assertThat(tecnicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTecnico() throws Exception {
        // Initialize the database
        tecnicoRepository.saveAndFlush(tecnico);
        int databaseSizeBeforeDelete = tecnicoRepository.findAll().size();

        // Get the tecnico
        restTecnicoMockMvc.perform(delete("/api/tecnicos/{id}", tecnico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tecnico> tecnicoList = tecnicoRepository.findAll();
        assertThat(tecnicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tecnico.class);
        Tecnico tecnico1 = new Tecnico();
        tecnico1.setId(1L);
        Tecnico tecnico2 = new Tecnico();
        tecnico2.setId(tecnico1.getId());
        assertThat(tecnico1).isEqualTo(tecnico2);
        tecnico2.setId(2L);
        assertThat(tecnico1).isNotEqualTo(tecnico2);
        tecnico1.setId(null);
        assertThat(tecnico1).isNotEqualTo(tecnico2);
    }
}
