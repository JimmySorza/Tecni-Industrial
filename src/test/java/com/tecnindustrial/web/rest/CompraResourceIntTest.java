package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.Compra;
import com.tecnindustrial.repository.CompraRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.tecnindustrial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompraResource REST controller.
 *
 * @see CompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class CompraResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompraMockMvc;

    private Compra compra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraResource compraResource = new CompraResource(compraRepository);
        this.restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
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
    public static Compra createEntity(EntityManager em) {
        Compra compra = new Compra()
            .fecha(DEFAULT_FECHA)
            .numero(DEFAULT_NUMERO)
            .total(DEFAULT_TOTAL);
        return compra;
    }

    @Before
    public void initTest() {
        compra = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompra() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compra)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCompra.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCompra.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra with an existing ID
        compra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compra)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompras() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompra() throws Exception {
        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Update the compra
        Compra updatedCompra = compraRepository.findOne(compra.getId());
        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
        em.detach(updatedCompra);
        updatedCompra
            .fecha(UPDATED_FECHA)
            .numero(UPDATED_NUMERO)
            .total(UPDATED_TOTAL);

        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompra)))
            .andExpect(status().isOk());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCompra.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCompra.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCompra() throws Exception {
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Create the Compra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compra)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        int databaseSizeBeforeDelete = compraRepository.findAll().size();

        // Get the compra
        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compra.class);
        Compra compra1 = new Compra();
        compra1.setId(1L);
        Compra compra2 = new Compra();
        compra2.setId(compra1.getId());
        assertThat(compra1).isEqualTo(compra2);
        compra2.setId(2L);
        assertThat(compra1).isNotEqualTo(compra2);
        compra1.setId(null);
        assertThat(compra1).isNotEqualTo(compra2);
    }
}
