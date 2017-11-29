package com.tecnindustrial.web.rest;

import com.tecnindustrial.TecniIndustrialApp;

import com.tecnindustrial.domain.OrdenReparacion;
import com.tecnindustrial.repository.OrdenReparacionRepository;
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
 * Test class for the OrdenReparacionResource REST controller.
 *
 * @see OrdenReparacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TecniIndustrialApp.class)
public class OrdenReparacionResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GARANTIA = false;
    private static final Boolean UPDATED_GARANTIA = true;

    private static final String DEFAULT_MAQUINA = "AAAAAAAAAA";
    private static final String UPDATED_MAQUINA = "BBBBBBBBBB";

    private static final String DEFAULT_FALLA = "AAAAAAAAAA";
    private static final String UPDATED_FALLA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_PROMETIDO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PROMETIDO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACCESORIOS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESORIOS = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_INFORME_TECNICO = "AAAAAAAAAA";
    private static final String UPDATED_INFORME_TECNICO = "BBBBBBBBBB";

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private OrdenReparacionRepository ordenReparacionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdenReparacionMockMvc;

    private OrdenReparacion ordenReparacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenReparacionResource ordenReparacionResource = new OrdenReparacionResource(ordenReparacionRepository);
        this.restOrdenReparacionMockMvc = MockMvcBuilders.standaloneSetup(ordenReparacionResource)
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
    public static OrdenReparacion createEntity(EntityManager em) {
        OrdenReparacion ordenReparacion = new OrdenReparacion()
            .fecha(DEFAULT_FECHA)
            .garantia(DEFAULT_GARANTIA)
            .maquina(DEFAULT_MAQUINA)
            .falla(DEFAULT_FALLA)
            .fechaPrometido(DEFAULT_FECHA_PROMETIDO)
            .accesorios(DEFAULT_ACCESORIOS)
            .observaciones(DEFAULT_OBSERVACIONES)
            .informeTecnico(DEFAULT_INFORME_TECNICO)
            .total(DEFAULT_TOTAL);
        return ordenReparacion;
    }

    @Before
    public void initTest() {
        ordenReparacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdenReparacion() throws Exception {
        int databaseSizeBeforeCreate = ordenReparacionRepository.findAll().size();

        // Create the OrdenReparacion
        restOrdenReparacionMockMvc.perform(post("/api/orden-reparacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenReparacion)))
            .andExpect(status().isCreated());

        // Validate the OrdenReparacion in the database
        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeCreate + 1);
        OrdenReparacion testOrdenReparacion = ordenReparacionList.get(ordenReparacionList.size() - 1);
        assertThat(testOrdenReparacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOrdenReparacion.isGarantia()).isEqualTo(DEFAULT_GARANTIA);
        assertThat(testOrdenReparacion.getMaquina()).isEqualTo(DEFAULT_MAQUINA);
        assertThat(testOrdenReparacion.getFalla()).isEqualTo(DEFAULT_FALLA);
        assertThat(testOrdenReparacion.getFechaPrometido()).isEqualTo(DEFAULT_FECHA_PROMETIDO);
        assertThat(testOrdenReparacion.getAccesorios()).isEqualTo(DEFAULT_ACCESORIOS);
        assertThat(testOrdenReparacion.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testOrdenReparacion.getInformeTecnico()).isEqualTo(DEFAULT_INFORME_TECNICO);
        assertThat(testOrdenReparacion.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createOrdenReparacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenReparacionRepository.findAll().size();

        // Create the OrdenReparacion with an existing ID
        ordenReparacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenReparacionMockMvc.perform(post("/api/orden-reparacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenReparacion)))
            .andExpect(status().isBadRequest());

        // Validate the OrdenReparacion in the database
        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMaquinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenReparacionRepository.findAll().size();
        // set the field null
        ordenReparacion.setMaquina(null);

        // Create the OrdenReparacion, which fails.

        restOrdenReparacionMockMvc.perform(post("/api/orden-reparacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenReparacion)))
            .andExpect(status().isBadRequest());

        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdenReparacions() throws Exception {
        // Initialize the database
        ordenReparacionRepository.saveAndFlush(ordenReparacion);

        // Get all the ordenReparacionList
        restOrdenReparacionMockMvc.perform(get("/api/orden-reparacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordenReparacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].garantia").value(hasItem(DEFAULT_GARANTIA.booleanValue())))
            .andExpect(jsonPath("$.[*].maquina").value(hasItem(DEFAULT_MAQUINA.toString())))
            .andExpect(jsonPath("$.[*].falla").value(hasItem(DEFAULT_FALLA.toString())))
            .andExpect(jsonPath("$.[*].fechaPrometido").value(hasItem(DEFAULT_FECHA_PROMETIDO.toString())))
            .andExpect(jsonPath("$.[*].accesorios").value(hasItem(DEFAULT_ACCESORIOS.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].informeTecnico").value(hasItem(DEFAULT_INFORME_TECNICO.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getOrdenReparacion() throws Exception {
        // Initialize the database
        ordenReparacionRepository.saveAndFlush(ordenReparacion);

        // Get the ordenReparacion
        restOrdenReparacionMockMvc.perform(get("/api/orden-reparacions/{id}", ordenReparacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordenReparacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.garantia").value(DEFAULT_GARANTIA.booleanValue()))
            .andExpect(jsonPath("$.maquina").value(DEFAULT_MAQUINA.toString()))
            .andExpect(jsonPath("$.falla").value(DEFAULT_FALLA.toString()))
            .andExpect(jsonPath("$.fechaPrometido").value(DEFAULT_FECHA_PROMETIDO.toString()))
            .andExpect(jsonPath("$.accesorios").value(DEFAULT_ACCESORIOS.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.informeTecnico").value(DEFAULT_INFORME_TECNICO.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenReparacion() throws Exception {
        // Get the ordenReparacion
        restOrdenReparacionMockMvc.perform(get("/api/orden-reparacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenReparacion() throws Exception {
        // Initialize the database
        ordenReparacionRepository.saveAndFlush(ordenReparacion);
        int databaseSizeBeforeUpdate = ordenReparacionRepository.findAll().size();

        // Update the ordenReparacion
        OrdenReparacion updatedOrdenReparacion = ordenReparacionRepository.findOne(ordenReparacion.getId());
        // Disconnect from session so that the updates on updatedOrdenReparacion are not directly saved in db
        em.detach(updatedOrdenReparacion);
        updatedOrdenReparacion
            .fecha(UPDATED_FECHA)
            .garantia(UPDATED_GARANTIA)
            .maquina(UPDATED_MAQUINA)
            .falla(UPDATED_FALLA)
            .fechaPrometido(UPDATED_FECHA_PROMETIDO)
            .accesorios(UPDATED_ACCESORIOS)
            .observaciones(UPDATED_OBSERVACIONES)
            .informeTecnico(UPDATED_INFORME_TECNICO)
            .total(UPDATED_TOTAL);

        restOrdenReparacionMockMvc.perform(put("/api/orden-reparacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdenReparacion)))
            .andExpect(status().isOk());

        // Validate the OrdenReparacion in the database
        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeUpdate);
        OrdenReparacion testOrdenReparacion = ordenReparacionList.get(ordenReparacionList.size() - 1);
        assertThat(testOrdenReparacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOrdenReparacion.isGarantia()).isEqualTo(UPDATED_GARANTIA);
        assertThat(testOrdenReparacion.getMaquina()).isEqualTo(UPDATED_MAQUINA);
        assertThat(testOrdenReparacion.getFalla()).isEqualTo(UPDATED_FALLA);
        assertThat(testOrdenReparacion.getFechaPrometido()).isEqualTo(UPDATED_FECHA_PROMETIDO);
        assertThat(testOrdenReparacion.getAccesorios()).isEqualTo(UPDATED_ACCESORIOS);
        assertThat(testOrdenReparacion.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testOrdenReparacion.getInformeTecnico()).isEqualTo(UPDATED_INFORME_TECNICO);
        assertThat(testOrdenReparacion.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdenReparacion() throws Exception {
        int databaseSizeBeforeUpdate = ordenReparacionRepository.findAll().size();

        // Create the OrdenReparacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdenReparacionMockMvc.perform(put("/api/orden-reparacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenReparacion)))
            .andExpect(status().isCreated());

        // Validate the OrdenReparacion in the database
        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdenReparacion() throws Exception {
        // Initialize the database
        ordenReparacionRepository.saveAndFlush(ordenReparacion);
        int databaseSizeBeforeDelete = ordenReparacionRepository.findAll().size();

        // Get the ordenReparacion
        restOrdenReparacionMockMvc.perform(delete("/api/orden-reparacions/{id}", ordenReparacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdenReparacion> ordenReparacionList = ordenReparacionRepository.findAll();
        assertThat(ordenReparacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdenReparacion.class);
        OrdenReparacion ordenReparacion1 = new OrdenReparacion();
        ordenReparacion1.setId(1L);
        OrdenReparacion ordenReparacion2 = new OrdenReparacion();
        ordenReparacion2.setId(ordenReparacion1.getId());
        assertThat(ordenReparacion1).isEqualTo(ordenReparacion2);
        ordenReparacion2.setId(2L);
        assertThat(ordenReparacion1).isNotEqualTo(ordenReparacion2);
        ordenReparacion1.setId(null);
        assertThat(ordenReparacion1).isNotEqualTo(ordenReparacion2);
    }
}
