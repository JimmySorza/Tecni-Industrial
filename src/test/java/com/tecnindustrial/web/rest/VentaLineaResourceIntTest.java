package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.VentaLinea;
import com.tecnindustrial.repository.VentaLineaRepository;
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
 * Test class for the VentaLineaResource REST controller.
 *
 * @see VentaLineaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class VentaLineaResourceIntTest {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    @Autowired
    private VentaLineaRepository ventaLineaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVentaLineaMockMvc;

    private VentaLinea ventaLinea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VentaLineaResource ventaLineaResource = new VentaLineaResource(ventaLineaRepository);
        this.restVentaLineaMockMvc = MockMvcBuilders.standaloneSetup(ventaLineaResource)
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
    public static VentaLinea createEntity(EntityManager em) {
        VentaLinea ventaLinea = new VentaLinea()
            .cantidad(DEFAULT_CANTIDAD);
        return ventaLinea;
    }

    @Before
    public void initTest() {
        ventaLinea = createEntity(em);
    }

    @Test
    @Transactional
    public void createVentaLinea() throws Exception {
        int databaseSizeBeforeCreate = ventaLineaRepository.findAll().size();

        // Create the VentaLinea
        restVentaLineaMockMvc.perform(post("/api/venta-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaLinea)))
            .andExpect(status().isCreated());

        // Validate the VentaLinea in the database
        List<VentaLinea> ventaLineaList = ventaLineaRepository.findAll();
        assertThat(ventaLineaList).hasSize(databaseSizeBeforeCreate + 1);
        VentaLinea testVentaLinea = ventaLineaList.get(ventaLineaList.size() - 1);
        assertThat(testVentaLinea.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createVentaLineaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaLineaRepository.findAll().size();

        // Create the VentaLinea with an existing ID
        ventaLinea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaLineaMockMvc.perform(post("/api/venta-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaLinea)))
            .andExpect(status().isBadRequest());

        // Validate the VentaLinea in the database
        List<VentaLinea> ventaLineaList = ventaLineaRepository.findAll();
        assertThat(ventaLineaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVentaLineas() throws Exception {
        // Initialize the database
        ventaLineaRepository.saveAndFlush(ventaLinea);

        // Get all the ventaLineaList
        restVentaLineaMockMvc.perform(get("/api/venta-lineas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventaLinea.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getVentaLinea() throws Exception {
        // Initialize the database
        ventaLineaRepository.saveAndFlush(ventaLinea);

        // Get the ventaLinea
        restVentaLineaMockMvc.perform(get("/api/venta-lineas/{id}", ventaLinea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ventaLinea.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVentaLinea() throws Exception {
        // Get the ventaLinea
        restVentaLineaMockMvc.perform(get("/api/venta-lineas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVentaLinea() throws Exception {
        // Initialize the database
        ventaLineaRepository.saveAndFlush(ventaLinea);
        int databaseSizeBeforeUpdate = ventaLineaRepository.findAll().size();

        // Update the ventaLinea
        VentaLinea updatedVentaLinea = ventaLineaRepository.findOne(ventaLinea.getId());
        // Disconnect from session so that the updates on updatedVentaLinea are not directly saved in db
        em.detach(updatedVentaLinea);
        updatedVentaLinea
            .cantidad(UPDATED_CANTIDAD);

        restVentaLineaMockMvc.perform(put("/api/venta-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVentaLinea)))
            .andExpect(status().isOk());

        // Validate the VentaLinea in the database
        List<VentaLinea> ventaLineaList = ventaLineaRepository.findAll();
        assertThat(ventaLineaList).hasSize(databaseSizeBeforeUpdate);
        VentaLinea testVentaLinea = ventaLineaList.get(ventaLineaList.size() - 1);
        assertThat(testVentaLinea.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingVentaLinea() throws Exception {
        int databaseSizeBeforeUpdate = ventaLineaRepository.findAll().size();

        // Create the VentaLinea

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVentaLineaMockMvc.perform(put("/api/venta-lineas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaLinea)))
            .andExpect(status().isCreated());

        // Validate the VentaLinea in the database
        List<VentaLinea> ventaLineaList = ventaLineaRepository.findAll();
        assertThat(ventaLineaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVentaLinea() throws Exception {
        // Initialize the database
        ventaLineaRepository.saveAndFlush(ventaLinea);
        int databaseSizeBeforeDelete = ventaLineaRepository.findAll().size();

        // Get the ventaLinea
        restVentaLineaMockMvc.perform(delete("/api/venta-lineas/{id}", ventaLinea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VentaLinea> ventaLineaList = ventaLineaRepository.findAll();
        assertThat(ventaLineaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaLinea.class);
        VentaLinea ventaLinea1 = new VentaLinea();
        ventaLinea1.setId(1L);
        VentaLinea ventaLinea2 = new VentaLinea();
        ventaLinea2.setId(ventaLinea1.getId());
        assertThat(ventaLinea1).isEqualTo(ventaLinea2);
        ventaLinea2.setId(2L);
        assertThat(ventaLinea1).isNotEqualTo(ventaLinea2);
        ventaLinea1.setId(null);
        assertThat(ventaLinea1).isNotEqualTo(ventaLinea2);
    }
}
