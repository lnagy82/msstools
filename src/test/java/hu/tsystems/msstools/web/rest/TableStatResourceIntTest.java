package hu.tsystems.msstools.web.rest;

import hu.tsystems.msstools.MsstoolsApp;

import hu.tsystems.msstools.domain.TableStat;
import hu.tsystems.msstools.repository.TableStatRepository;
import hu.tsystems.msstools.service.TableStatService;
import hu.tsystems.msstools.service.dto.TableStatDTO;
import hu.tsystems.msstools.service.mapper.TableStatMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TableStatResource REST controller.
 *
 * @see TableStatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MsstoolsApp.class)
public class TableStatResourceIntTest {

    private static final String DEFAULT_SCHEMANAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMANAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELNAME = "AAAAAAAAAA";
    private static final String UPDATED_RELNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SEQ_SCAN = new BigDecimal(1);
    private static final BigDecimal UPDATED_SEQ_SCAN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SEQ_TUP_READ = new BigDecimal(1);
    private static final BigDecimal UPDATED_SEQ_TUP_READ = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IDX_SCAN = new BigDecimal(1);
    private static final BigDecimal UPDATED_IDX_SCAN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IDX_TUP_FETCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_IDX_TUP_FETCH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_TUP_INS = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_TUP_INS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_TUP_UPD = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_TUP_UPD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_TUP_DEL = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_TUP_DEL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_TUP_HOT_UPD = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_TUP_HOT_UPD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_LIVE_TUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_LIVE_TUP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_DEAD_TUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_DEAD_TUP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VACUUM_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VACUUM_COUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AUTOVACUUM_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AUTOVACUUM_COUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ANALYZE_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANALYZE_COUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AUTOANALYZE_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AUTOANALYZE_COUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_UPDATE_NUMBER = 1;
    private static final Integer UPDATED_UPDATE_NUMBER = 2;

    private static final LocalDate DEFAULT_LAST_VACUUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_VACUUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_AUTOVACUUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_AUTOVACUUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_ANALYZE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_ANALYZE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_AUTOANALYZE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_AUTOANALYZE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TableStatRepository tableStatRepository;

    @Autowired
    private TableStatMapper tableStatMapper;

    @Autowired
    private TableStatService tableStatService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTableStatMockMvc;

    private TableStat tableStat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TableStatResource tableStatResource = new TableStatResource(tableStatService);
        this.restTableStatMockMvc = MockMvcBuilders.standaloneSetup(tableStatResource)
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
    public static TableStat createEntity(EntityManager em) {
        TableStat tableStat = new TableStat()
            .schemaname(DEFAULT_SCHEMANAME)
            .relname(DEFAULT_RELNAME)
            .seqScan(DEFAULT_SEQ_SCAN)
            .seqTupRead(DEFAULT_SEQ_TUP_READ)
            .idxScan(DEFAULT_IDX_SCAN)
            .idxTupFetch(DEFAULT_IDX_TUP_FETCH)
            .nTupIns(DEFAULT_N_TUP_INS)
            .nTupUpd(DEFAULT_N_TUP_UPD)
            .nTupDel(DEFAULT_N_TUP_DEL)
            .nTupHotUpd(DEFAULT_N_TUP_HOT_UPD)
            .nLiveTup(DEFAULT_N_LIVE_TUP)
            .nDeadTup(DEFAULT_N_DEAD_TUP)
            .vacuumCount(DEFAULT_VACUUM_COUNT)
            .autovacuumCount(DEFAULT_AUTOVACUUM_COUNT)
            .analyzeCount(DEFAULT_ANALYZE_COUNT)
            .autoanalyzeCount(DEFAULT_AUTOANALYZE_COUNT)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateNumber(DEFAULT_UPDATE_NUMBER)
            .lastVacuum(DEFAULT_LAST_VACUUM)
            .lastAutovacuum(DEFAULT_LAST_AUTOVACUUM)
            .lastAnalyze(DEFAULT_LAST_ANALYZE)
            .lastAutoanalyze(DEFAULT_LAST_AUTOANALYZE);
        return tableStat;
    }

    @Before
    public void initTest() {
        tableStat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTableStat() throws Exception {
        int databaseSizeBeforeCreate = tableStatRepository.findAll().size();

        // Create the TableStat
        TableStatDTO tableStatDTO = tableStatMapper.toDto(tableStat);
        restTableStatMockMvc.perform(post("/api/table-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableStatDTO)))
            .andExpect(status().isCreated());

        // Validate the TableStat in the database
        List<TableStat> tableStatList = tableStatRepository.findAll();
        assertThat(tableStatList).hasSize(databaseSizeBeforeCreate + 1);
        TableStat testTableStat = tableStatList.get(tableStatList.size() - 1);
        assertThat(testTableStat.getSchemaname()).isEqualTo(DEFAULT_SCHEMANAME);
        assertThat(testTableStat.getRelname()).isEqualTo(DEFAULT_RELNAME);
        assertThat(testTableStat.getSeqScan()).isEqualTo(DEFAULT_SEQ_SCAN);
        assertThat(testTableStat.getSeqTupRead()).isEqualTo(DEFAULT_SEQ_TUP_READ);
        assertThat(testTableStat.getIdxScan()).isEqualTo(DEFAULT_IDX_SCAN);
        assertThat(testTableStat.getIdxTupFetch()).isEqualTo(DEFAULT_IDX_TUP_FETCH);
        assertThat(testTableStat.getnTupIns()).isEqualTo(DEFAULT_N_TUP_INS);
        assertThat(testTableStat.getnTupUpd()).isEqualTo(DEFAULT_N_TUP_UPD);
        assertThat(testTableStat.getnTupDel()).isEqualTo(DEFAULT_N_TUP_DEL);
        assertThat(testTableStat.getnTupHotUpd()).isEqualTo(DEFAULT_N_TUP_HOT_UPD);
        assertThat(testTableStat.getnLiveTup()).isEqualTo(DEFAULT_N_LIVE_TUP);
        assertThat(testTableStat.getnDeadTup()).isEqualTo(DEFAULT_N_DEAD_TUP);
        assertThat(testTableStat.getVacuumCount()).isEqualTo(DEFAULT_VACUUM_COUNT);
        assertThat(testTableStat.getAutovacuumCount()).isEqualTo(DEFAULT_AUTOVACUUM_COUNT);
        assertThat(testTableStat.getAnalyzeCount()).isEqualTo(DEFAULT_ANALYZE_COUNT);
        assertThat(testTableStat.getAutoanalyzeCount()).isEqualTo(DEFAULT_AUTOANALYZE_COUNT);
        assertThat(testTableStat.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testTableStat.getUpdateNumber()).isEqualTo(DEFAULT_UPDATE_NUMBER);
        assertThat(testTableStat.getLastVacuum()).isEqualTo(DEFAULT_LAST_VACUUM);
        assertThat(testTableStat.getLastAutovacuum()).isEqualTo(DEFAULT_LAST_AUTOVACUUM);
        assertThat(testTableStat.getLastAnalyze()).isEqualTo(DEFAULT_LAST_ANALYZE);
        assertThat(testTableStat.getLastAutoanalyze()).isEqualTo(DEFAULT_LAST_AUTOANALYZE);
    }

    @Test
    @Transactional
    public void createTableStatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableStatRepository.findAll().size();

        // Create the TableStat with an existing ID
        tableStat.setId(1L);
        TableStatDTO tableStatDTO = tableStatMapper.toDto(tableStat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableStatMockMvc.perform(post("/api/table-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableStatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TableStat> tableStatList = tableStatRepository.findAll();
        assertThat(tableStatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTableStats() throws Exception {
        // Initialize the database
        tableStatRepository.saveAndFlush(tableStat);

        // Get all the tableStatList
        restTableStatMockMvc.perform(get("/api/table-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableStat.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemaname").value(hasItem(DEFAULT_SCHEMANAME.toString())))
            .andExpect(jsonPath("$.[*].relname").value(hasItem(DEFAULT_RELNAME.toString())))
            .andExpect(jsonPath("$.[*].seqScan").value(hasItem(DEFAULT_SEQ_SCAN.intValue())))
            .andExpect(jsonPath("$.[*].seqTupRead").value(hasItem(DEFAULT_SEQ_TUP_READ.intValue())))
            .andExpect(jsonPath("$.[*].idxScan").value(hasItem(DEFAULT_IDX_SCAN.intValue())))
            .andExpect(jsonPath("$.[*].idxTupFetch").value(hasItem(DEFAULT_IDX_TUP_FETCH.intValue())))
            .andExpect(jsonPath("$.[*].nTupIns").value(hasItem(DEFAULT_N_TUP_INS.intValue())))
            .andExpect(jsonPath("$.[*].nTupUpd").value(hasItem(DEFAULT_N_TUP_UPD.intValue())))
            .andExpect(jsonPath("$.[*].nTupDel").value(hasItem(DEFAULT_N_TUP_DEL.intValue())))
            .andExpect(jsonPath("$.[*].nTupHotUpd").value(hasItem(DEFAULT_N_TUP_HOT_UPD.intValue())))
            .andExpect(jsonPath("$.[*].nLiveTup").value(hasItem(DEFAULT_N_LIVE_TUP.intValue())))
            .andExpect(jsonPath("$.[*].nDeadTup").value(hasItem(DEFAULT_N_DEAD_TUP.intValue())))
            .andExpect(jsonPath("$.[*].vacuumCount").value(hasItem(DEFAULT_VACUUM_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].autovacuumCount").value(hasItem(DEFAULT_AUTOVACUUM_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].analyzeCount").value(hasItem(DEFAULT_ANALYZE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].autoanalyzeCount").value(hasItem(DEFAULT_AUTOANALYZE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateNumber").value(hasItem(DEFAULT_UPDATE_NUMBER)))
            .andExpect(jsonPath("$.[*].lastVacuum").value(hasItem(DEFAULT_LAST_VACUUM.toString())))
            .andExpect(jsonPath("$.[*].lastAutovacuum").value(hasItem(DEFAULT_LAST_AUTOVACUUM.toString())))
            .andExpect(jsonPath("$.[*].lastAnalyze").value(hasItem(DEFAULT_LAST_ANALYZE.toString())))
            .andExpect(jsonPath("$.[*].lastAutoanalyze").value(hasItem(DEFAULT_LAST_AUTOANALYZE.toString())));
    }

    @Test
    @Transactional
    public void getTableStat() throws Exception {
        // Initialize the database
        tableStatRepository.saveAndFlush(tableStat);

        // Get the tableStat
        restTableStatMockMvc.perform(get("/api/table-stats/{id}", tableStat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tableStat.getId().intValue()))
            .andExpect(jsonPath("$.schemaname").value(DEFAULT_SCHEMANAME.toString()))
            .andExpect(jsonPath("$.relname").value(DEFAULT_RELNAME.toString()))
            .andExpect(jsonPath("$.seqScan").value(DEFAULT_SEQ_SCAN.intValue()))
            .andExpect(jsonPath("$.seqTupRead").value(DEFAULT_SEQ_TUP_READ.intValue()))
            .andExpect(jsonPath("$.idxScan").value(DEFAULT_IDX_SCAN.intValue()))
            .andExpect(jsonPath("$.idxTupFetch").value(DEFAULT_IDX_TUP_FETCH.intValue()))
            .andExpect(jsonPath("$.nTupIns").value(DEFAULT_N_TUP_INS.intValue()))
            .andExpect(jsonPath("$.nTupUpd").value(DEFAULT_N_TUP_UPD.intValue()))
            .andExpect(jsonPath("$.nTupDel").value(DEFAULT_N_TUP_DEL.intValue()))
            .andExpect(jsonPath("$.nTupHotUpd").value(DEFAULT_N_TUP_HOT_UPD.intValue()))
            .andExpect(jsonPath("$.nLiveTup").value(DEFAULT_N_LIVE_TUP.intValue()))
            .andExpect(jsonPath("$.nDeadTup").value(DEFAULT_N_DEAD_TUP.intValue()))
            .andExpect(jsonPath("$.vacuumCount").value(DEFAULT_VACUUM_COUNT.intValue()))
            .andExpect(jsonPath("$.autovacuumCount").value(DEFAULT_AUTOVACUUM_COUNT.intValue()))
            .andExpect(jsonPath("$.analyzeCount").value(DEFAULT_ANALYZE_COUNT.intValue()))
            .andExpect(jsonPath("$.autoanalyzeCount").value(DEFAULT_AUTOANALYZE_COUNT.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateNumber").value(DEFAULT_UPDATE_NUMBER))
            .andExpect(jsonPath("$.lastVacuum").value(DEFAULT_LAST_VACUUM.toString()))
            .andExpect(jsonPath("$.lastAutovacuum").value(DEFAULT_LAST_AUTOVACUUM.toString()))
            .andExpect(jsonPath("$.lastAnalyze").value(DEFAULT_LAST_ANALYZE.toString()))
            .andExpect(jsonPath("$.lastAutoanalyze").value(DEFAULT_LAST_AUTOANALYZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTableStat() throws Exception {
        // Get the tableStat
        restTableStatMockMvc.perform(get("/api/table-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTableStat() throws Exception {
        // Initialize the database
        tableStatRepository.saveAndFlush(tableStat);
        int databaseSizeBeforeUpdate = tableStatRepository.findAll().size();

        // Update the tableStat
        TableStat updatedTableStat = tableStatRepository.findOne(tableStat.getId());
        updatedTableStat
            .schemaname(UPDATED_SCHEMANAME)
            .relname(UPDATED_RELNAME)
            .seqScan(UPDATED_SEQ_SCAN)
            .seqTupRead(UPDATED_SEQ_TUP_READ)
            .idxScan(UPDATED_IDX_SCAN)
            .idxTupFetch(UPDATED_IDX_TUP_FETCH)
            .nTupIns(UPDATED_N_TUP_INS)
            .nTupUpd(UPDATED_N_TUP_UPD)
            .nTupDel(UPDATED_N_TUP_DEL)
            .nTupHotUpd(UPDATED_N_TUP_HOT_UPD)
            .nLiveTup(UPDATED_N_LIVE_TUP)
            .nDeadTup(UPDATED_N_DEAD_TUP)
            .vacuumCount(UPDATED_VACUUM_COUNT)
            .autovacuumCount(UPDATED_AUTOVACUUM_COUNT)
            .analyzeCount(UPDATED_ANALYZE_COUNT)
            .autoanalyzeCount(UPDATED_AUTOANALYZE_COUNT)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateNumber(UPDATED_UPDATE_NUMBER)
            .lastVacuum(UPDATED_LAST_VACUUM)
            .lastAutovacuum(UPDATED_LAST_AUTOVACUUM)
            .lastAnalyze(UPDATED_LAST_ANALYZE)
            .lastAutoanalyze(UPDATED_LAST_AUTOANALYZE);
        TableStatDTO tableStatDTO = tableStatMapper.toDto(updatedTableStat);

        restTableStatMockMvc.perform(put("/api/table-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableStatDTO)))
            .andExpect(status().isOk());

        // Validate the TableStat in the database
        List<TableStat> tableStatList = tableStatRepository.findAll();
        assertThat(tableStatList).hasSize(databaseSizeBeforeUpdate);
        TableStat testTableStat = tableStatList.get(tableStatList.size() - 1);
        assertThat(testTableStat.getSchemaname()).isEqualTo(UPDATED_SCHEMANAME);
        assertThat(testTableStat.getRelname()).isEqualTo(UPDATED_RELNAME);
        assertThat(testTableStat.getSeqScan()).isEqualTo(UPDATED_SEQ_SCAN);
        assertThat(testTableStat.getSeqTupRead()).isEqualTo(UPDATED_SEQ_TUP_READ);
        assertThat(testTableStat.getIdxScan()).isEqualTo(UPDATED_IDX_SCAN);
        assertThat(testTableStat.getIdxTupFetch()).isEqualTo(UPDATED_IDX_TUP_FETCH);
        assertThat(testTableStat.getnTupIns()).isEqualTo(UPDATED_N_TUP_INS);
        assertThat(testTableStat.getnTupUpd()).isEqualTo(UPDATED_N_TUP_UPD);
        assertThat(testTableStat.getnTupDel()).isEqualTo(UPDATED_N_TUP_DEL);
        assertThat(testTableStat.getnTupHotUpd()).isEqualTo(UPDATED_N_TUP_HOT_UPD);
        assertThat(testTableStat.getnLiveTup()).isEqualTo(UPDATED_N_LIVE_TUP);
        assertThat(testTableStat.getnDeadTup()).isEqualTo(UPDATED_N_DEAD_TUP);
        assertThat(testTableStat.getVacuumCount()).isEqualTo(UPDATED_VACUUM_COUNT);
        assertThat(testTableStat.getAutovacuumCount()).isEqualTo(UPDATED_AUTOVACUUM_COUNT);
        assertThat(testTableStat.getAnalyzeCount()).isEqualTo(UPDATED_ANALYZE_COUNT);
        assertThat(testTableStat.getAutoanalyzeCount()).isEqualTo(UPDATED_AUTOANALYZE_COUNT);
        assertThat(testTableStat.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testTableStat.getUpdateNumber()).isEqualTo(UPDATED_UPDATE_NUMBER);
        assertThat(testTableStat.getLastVacuum()).isEqualTo(UPDATED_LAST_VACUUM);
        assertThat(testTableStat.getLastAutovacuum()).isEqualTo(UPDATED_LAST_AUTOVACUUM);
        assertThat(testTableStat.getLastAnalyze()).isEqualTo(UPDATED_LAST_ANALYZE);
        assertThat(testTableStat.getLastAutoanalyze()).isEqualTo(UPDATED_LAST_AUTOANALYZE);
    }

    @Test
    @Transactional
    public void updateNonExistingTableStat() throws Exception {
        int databaseSizeBeforeUpdate = tableStatRepository.findAll().size();

        // Create the TableStat
        TableStatDTO tableStatDTO = tableStatMapper.toDto(tableStat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTableStatMockMvc.perform(put("/api/table-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableStatDTO)))
            .andExpect(status().isCreated());

        // Validate the TableStat in the database
        List<TableStat> tableStatList = tableStatRepository.findAll();
        assertThat(tableStatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTableStat() throws Exception {
        // Initialize the database
        tableStatRepository.saveAndFlush(tableStat);
        int databaseSizeBeforeDelete = tableStatRepository.findAll().size();

        // Get the tableStat
        restTableStatMockMvc.perform(delete("/api/table-stats/{id}", tableStat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TableStat> tableStatList = tableStatRepository.findAll();
        assertThat(tableStatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableStat.class);
        TableStat tableStat1 = new TableStat();
        tableStat1.setId(1L);
        TableStat tableStat2 = new TableStat();
        tableStat2.setId(tableStat1.getId());
        assertThat(tableStat1).isEqualTo(tableStat2);
        tableStat2.setId(2L);
        assertThat(tableStat1).isNotEqualTo(tableStat2);
        tableStat1.setId(null);
        assertThat(tableStat1).isNotEqualTo(tableStat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableStatDTO.class);
        TableStatDTO tableStatDTO1 = new TableStatDTO();
        tableStatDTO1.setId(1L);
        TableStatDTO tableStatDTO2 = new TableStatDTO();
        assertThat(tableStatDTO1).isNotEqualTo(tableStatDTO2);
        tableStatDTO2.setId(tableStatDTO1.getId());
        assertThat(tableStatDTO1).isEqualTo(tableStatDTO2);
        tableStatDTO2.setId(2L);
        assertThat(tableStatDTO1).isNotEqualTo(tableStatDTO2);
        tableStatDTO1.setId(null);
        assertThat(tableStatDTO1).isNotEqualTo(tableStatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tableStatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tableStatMapper.fromId(null)).isNull();
    }
}
