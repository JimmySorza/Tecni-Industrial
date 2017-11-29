package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.OrdenLinea;
import com.tecnindustrial.repository.OrdenLineaRepository;
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
 * Test class for the OrdenLineaResource REST controller.
 *
 * @see OrdenLineaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class OrdenLineaResourceIntTest {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    @Autowired
    private OrdenLineaRepository ordenLineaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdenLineaMockMvc;

    private OrdenLinea ordenLinea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenLineaResource ordenLineaResource = new OrdenLineaResource(ordenLineaRepository);
        this.restOrdenLineaMockMvc = MockMvcBuilders.standaloneSetup(ordenLineaResource)
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
    public static OrdenLinea createEntity(EntityManager em) {
        OrdenLinea ordenLinea = new OrdenLinea()
            .cantidad(DEFAULT_CANTIDAD);
        return ordenLinea;
    }

    @Before
    public void initTest() {
        ordenLinea = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdenLinea() throws Exception {
        int databaseSizeBeforeCreate = ordenLineaRepository.findAll().size();

        // Create the OrdenLinea
        restOrdenLineaMockMvc.perform(post("/api/orden-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenLinea)))
            .andExpect(status().isCreated());

        // Validate the OrdenLinea in the database
        List<OrdenLinea> ordenLineaList = ordenLineaRepository.findAll();
        assertThat(ordenLineaList).hasSize(databaseSizeBeforeCreate + 1);
        OrdenLinea testOrdenLinea = ordenLineaList.get(ordenLineaList.size() - 1);
        assertThat(testOrdenLinea.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createOrdenLineaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenLineaRepository.findAll().size();

        // Create the OrdenLinea with an existing ID
        ordenLinea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenLineaMockMvc.perform(post("/api/orden-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenLinea)))
            .andExpect(status().isBadRequest());

        // Validate the OrdenLinea in the database
        List<OrdenLinea> ordenLineaList = ordenLineaRepository.findAll();
        assertThat(ordenLineaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdenLineas() throws Exception {
        // Initialize the database
        ordenLineaRepository.saveAndFlush(ordenLinea);

        // Get all the ordenLineaList
        restOrdenLineaMockMvc.perform(get("/api/orden-lineas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordenLinea.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getOrdenLinea() throws Exception {
        // Initialize the database
        ordenLineaRepository.saveAndFlush(ordenLinea);

        // Get the ordenLinea
        restOrdenLineaMockMvc.perform(get("/api/orden-lineas/{id}", ordenLinea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordenLinea.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenLinea() throws Exception {
        // Get the ordenLinea
        restOrdenLineaMockMvc.perform(get("/api/orden-lineas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenLinea() throws Exception {
        // Initialize the database
        ordenLineaRepository.saveAndFlush(ordenLinea);
        int databaseSizeBeforeUpdate = ordenLineaRepository.findAll().size();

        // Update the ordenLinea
        OrdenLinea updatedOrdenLinea = ordenLineaRepository.findOne(ordenLinea.getId());
        // Disconnect from session so that the updates on updatedOrdenLinea are not directly saved in db
        em.detach(updatedOrdenLinea);
        updatedOrdenLinea
            .cantidad(UPDATED_CANTIDAD);

        restOrdenLineaMockMvc.perform(put("/api/orden-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdenLinea)))
            .andExpect(status().isOk());

        // Validate the OrdenLinea in the database
        List<OrdenLinea> ordenLineaList = ordenLineaRepository.findAll();
        assertThat(ordenLineaList).hasSize(databaseSizeBeforeUpdate);
        OrdenLinea testOrdenLinea = ordenLineaList.get(ordenLineaList.size() - 1);
        assertThat(testOrdenLinea.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdenLinea() throws Exception {
        int databaseSizeBeforeUpdate = ordenLineaRepository.findAll().size();

        // Create the OrdenLinea

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdenLineaMockMvc.perform(put("/api/orden-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenLinea)))
            .andExpect(status().isCreated());

        // Validate the OrdenLinea in the database
        List<OrdenLinea> ordenLineaList = ordenLineaRepository.findAll();
        assertThat(ordenLineaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdenLinea() throws Exception {
        // Initialize the database
        ordenLineaRepository.saveAndFlush(ordenLinea);
        int databaseSizeBeforeDelete = ordenLineaRepository.findAll().size();

        // Get the ordenLinea
        restOrdenLineaMockMvc.perform(delete("/api/orden-lineas/{id}", ordenLinea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdenLinea> ordenLineaList = ordenLineaRepository.findAll();
        assertThat(ordenLineaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdenLinea.class);
        OrdenLinea ordenLinea1 = new OrdenLinea();
        ordenLinea1.setId(1L);
        OrdenLinea ordenLinea2 = new OrdenLinea();
        ordenLinea2.setId(ordenLinea1.getId());
        assertThat(ordenLinea1).isEqualTo(ordenLinea2);
        ordenLinea2.setId(2L);
        assertThat(ordenLinea1).isNotEqualTo(ordenLinea2);
        ordenLinea1.setId(null);
        assertThat(ordenLinea1).isNotEqualTo(ordenLinea2);
    }
}
