package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.CompraLinea;
import com.tecnindustrial.repository.CompraLineaRepository;
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
 * Test class for the CompraLineaResource REST controller.
 *
 * @see CompraLineaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class CompraLineaResourceIntTest {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    @Autowired
    private CompraLineaRepository compraLineaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompraLineaMockMvc;

    private CompraLinea compraLinea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraLineaResource compraLineaResource = new CompraLineaResource(compraLineaRepository);
        this.restCompraLineaMockMvc = MockMvcBuilders.standaloneSetup(compraLineaResource)
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
    public static CompraLinea createEntity(EntityManager em) {
        CompraLinea compraLinea = new CompraLinea()
            .cantidad(DEFAULT_CANTIDAD);
        return compraLinea;
    }

    @Before
    public void initTest() {
        compraLinea = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompraLinea() throws Exception {
        int databaseSizeBeforeCreate = compraLineaRepository.findAll().size();

        // Create the CompraLinea
        restCompraLineaMockMvc.perform(post("/api/compra-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraLinea)))
            .andExpect(status().isCreated());

        // Validate the CompraLinea in the database
        List<CompraLinea> compraLineaList = compraLineaRepository.findAll();
        assertThat(compraLineaList).hasSize(databaseSizeBeforeCreate + 1);
        CompraLinea testCompraLinea = compraLineaList.get(compraLineaList.size() - 1);
        assertThat(testCompraLinea.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createCompraLineaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraLineaRepository.findAll().size();

        // Create the CompraLinea with an existing ID
        compraLinea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraLineaMockMvc.perform(post("/api/compra-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraLinea)))
            .andExpect(status().isBadRequest());

        // Validate the CompraLinea in the database
        List<CompraLinea> compraLineaList = compraLineaRepository.findAll();
        assertThat(compraLineaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompraLineas() throws Exception {
        // Initialize the database
        compraLineaRepository.saveAndFlush(compraLinea);

        // Get all the compraLineaList
        restCompraLineaMockMvc.perform(get("/api/compra-lineas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraLinea.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getCompraLinea() throws Exception {
        // Initialize the database
        compraLineaRepository.saveAndFlush(compraLinea);

        // Get the compraLinea
        restCompraLineaMockMvc.perform(get("/api/compra-lineas/{id}", compraLinea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compraLinea.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompraLinea() throws Exception {
        // Get the compraLinea
        restCompraLineaMockMvc.perform(get("/api/compra-lineas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompraLinea() throws Exception {
        // Initialize the database
        compraLineaRepository.saveAndFlush(compraLinea);
        int databaseSizeBeforeUpdate = compraLineaRepository.findAll().size();

        // Update the compraLinea
        CompraLinea updatedCompraLinea = compraLineaRepository.findOne(compraLinea.getId());
        // Disconnect from session so that the updates on updatedCompraLinea are not directly saved in db
        em.detach(updatedCompraLinea);
        updatedCompraLinea
            .cantidad(UPDATED_CANTIDAD);

        restCompraLineaMockMvc.perform(put("/api/compra-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompraLinea)))
            .andExpect(status().isOk());

        // Validate the CompraLinea in the database
        List<CompraLinea> compraLineaList = compraLineaRepository.findAll();
        assertThat(compraLineaList).hasSize(databaseSizeBeforeUpdate);
        CompraLinea testCompraLinea = compraLineaList.get(compraLineaList.size() - 1);
        assertThat(testCompraLinea.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingCompraLinea() throws Exception {
        int databaseSizeBeforeUpdate = compraLineaRepository.findAll().size();

        // Create the CompraLinea

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompraLineaMockMvc.perform(put("/api/compra-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraLinea)))
            .andExpect(status().isCreated());

        // Validate the CompraLinea in the database
        List<CompraLinea> compraLineaList = compraLineaRepository.findAll();
        assertThat(compraLineaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompraLinea() throws Exception {
        // Initialize the database
        compraLineaRepository.saveAndFlush(compraLinea);
        int databaseSizeBeforeDelete = compraLineaRepository.findAll().size();

        // Get the compraLinea
        restCompraLineaMockMvc.perform(delete("/api/compra-lineas/{id}", compraLinea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompraLinea> compraLineaList = compraLineaRepository.findAll();
        assertThat(compraLineaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraLinea.class);
        CompraLinea compraLinea1 = new CompraLinea();
        compraLinea1.setId(1L);
        CompraLinea compraLinea2 = new CompraLinea();
        compraLinea2.setId(compraLinea1.getId());
        assertThat(compraLinea1).isEqualTo(compraLinea2);
        compraLinea2.setId(2L);
        assertThat(compraLinea1).isNotEqualTo(compraLinea2);
        compraLinea1.setId(null);
        assertThat(compraLinea1).isNotEqualTo(compraLinea2);
    }
}
