package hu.tsystems.msstools.web.rest;

import hu.tsystems.msstools.MsstoolsApp;

import hu.tsystems.msstools.domain.System;
import hu.tsystems.msstools.repository.SystemRepository;
import hu.tsystems.msstools.service.SystemService;
import hu.tsystems.msstools.service.dto.SystemDTO;
import hu.tsystems.msstools.service.mapper.SystemMapper;
import hu.tsystems.msstools.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SystemResource REST controller.
 *
 * @see SystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MsstoolsApp.class)
public class SystemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SystemService systemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemMockMvc;

    private System system;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SystemResource systemResource = new SystemResource(systemService);
        this.restSystemMockMvc = MockMvcBuilders.standaloneSetup(systemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createEntity(EntityManager em) {
        System system = new System()
            .name(DEFAULT_NAME)
            .ip(DEFAULT_IP)
            .port(DEFAULT_PORT);
        return system;
    }

    @Before
    public void initTest() {
        system = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystem() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate + 1);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSystem.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testSystem.getPort()).isEqualTo(DEFAULT_PORT);
    }

    @Test
    @Transactional
    public void createSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System with an existing ID
        system.setId(1L);
        SystemDTO systemDTO = systemMapper.toDto(system);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemRepository.findAll().size();
        // set the field null
        system.setName(null);

        // Create the System, which fails.
        SystemDTO systemDTO = systemMapper.toDto(system);

        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemRepository.findAll().size();
        // set the field null
        system.setIp(null);

        // Create the System, which fails.
        SystemDTO systemDTO = systemMapper.toDto(system);

        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemRepository.findAll().size();
        // set the field null
        system.setPort(null);

        // Create the System, which fails.
        SystemDTO systemDTO = systemMapper.toDto(system);

        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystems() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)));
    }

    @Test
    @Transactional
    public void getSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(system.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT));
    }

    @Test
    @Transactional
    public void getNonExistingSystem() throws Exception {
        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system
        System updatedSystem = systemRepository.findOne(system.getId());
        updatedSystem
            .name(UPDATED_NAME)
            .ip(UPDATED_IP)
            .port(UPDATED_PORT);
        SystemDTO systemDTO = systemMapper.toDto(updatedSystem);

        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSystem.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testSystem.getPort()).isEqualTo(UPDATED_PORT);
    }

    @Test
    @Transactional
    public void updateNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        int databaseSizeBeforeDelete = systemRepository.findAll().size();

        // Get the system
        restSystemMockMvc.perform(delete("/api/systems/{id}", system.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(System.class);
        System system1 = new System();
        system1.setId(1L);
        System system2 = new System();
        system2.setId(system1.getId());
        assertThat(system1).isEqualTo(system2);
        system2.setId(2L);
        assertThat(system1).isNotEqualTo(system2);
        system1.setId(null);
        assertThat(system1).isNotEqualTo(system2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemDTO.class);
        SystemDTO systemDTO1 = new SystemDTO();
        systemDTO1.setId(1L);
        SystemDTO systemDTO2 = new SystemDTO();
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO2.setId(systemDTO1.getId());
        assertThat(systemDTO1).isEqualTo(systemDTO2);
        systemDTO2.setId(2L);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO1.setId(null);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemMapper.fromId(null)).isNull();
    }
}
