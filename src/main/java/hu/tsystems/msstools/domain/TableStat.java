package hu.tsystems.msstools.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TableStat.
 */
@Entity
@Table(name = "table_stat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TableStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "schemaname")
    private String schemaname;

    @Column(name = "relname")
    private String relname;

    @Column(name = "seq_scan", precision=10, scale=2)
    private BigDecimal seqScan;

    @Column(name = "seq_tup_read", precision=10, scale=2)
    private BigDecimal seqTupRead;

    @Column(name = "idx_scan", precision=10, scale=2)
    private BigDecimal idxScan;

    @Column(name = "idx_tup_fetch", precision=10, scale=2)
    private BigDecimal idxTupFetch;

    @Column(name = "n_tup_ins", precision=10, scale=2)
    private BigDecimal nTupIns;

    @Column(name = "n_tup_upd", precision=10, scale=2)
    private BigDecimal nTupUpd;

    @Column(name = "n_tup_del", precision=10, scale=2)
    private BigDecimal nTupDel;

    @Column(name = "n_tup_hot_upd", precision=10, scale=2)
    private BigDecimal nTupHotUpd;

    @Column(name = "n_live_tup", precision=10, scale=2)
    private BigDecimal nLiveTup;

    @Column(name = "n_dead_tup", precision=10, scale=2)
    private BigDecimal nDeadTup;

    @Column(name = "vacuum_count", precision=10, scale=2)
    private BigDecimal vacuumCount;

    @Column(name = "autovacuum_count", precision=10, scale=2)
    private BigDecimal autovacuumCount;

    @Column(name = "analyze_count", precision=10, scale=2)
    private BigDecimal analyzeCount;

    @Column(name = "autoanalyze_count", precision=10, scale=2)
    private BigDecimal autoanalyzeCount;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "update_number")
    private Integer updateNumber;

    @Column(name = "last_vacuum")
    private LocalDate lastVacuum;

    @Column(name = "last_autovacuum")
    private LocalDate lastAutovacuum;

    @Column(name = "last_analyze")
    private LocalDate lastAnalyze;

    @Column(name = "last_autoanalyze")
    private LocalDate lastAutoanalyze;

    @ManyToOne
    private System system;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public TableStat schemaname(String schemaname) {
        this.schemaname = schemaname;
        return this;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getRelname() {
        return relname;
    }

    public TableStat relname(String relname) {
        this.relname = relname;
        return this;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public BigDecimal getSeqScan() {
        return seqScan;
    }

    public TableStat seqScan(BigDecimal seqScan) {
        this.seqScan = seqScan;
        return this;
    }

    public void setSeqScan(BigDecimal seqScan) {
        this.seqScan = seqScan;
    }

    public BigDecimal getSeqTupRead() {
        return seqTupRead;
    }

    public TableStat seqTupRead(BigDecimal seqTupRead) {
        this.seqTupRead = seqTupRead;
        return this;
    }

    public void setSeqTupRead(BigDecimal seqTupRead) {
        this.seqTupRead = seqTupRead;
    }

    public BigDecimal getIdxScan() {
        return idxScan;
    }

    public TableStat idxScan(BigDecimal idxScan) {
        this.idxScan = idxScan;
        return this;
    }

    public void setIdxScan(BigDecimal idxScan) {
        this.idxScan = idxScan;
    }

    public BigDecimal getIdxTupFetch() {
        return idxTupFetch;
    }

    public TableStat idxTupFetch(BigDecimal idxTupFetch) {
        this.idxTupFetch = idxTupFetch;
        return this;
    }

    public void setIdxTupFetch(BigDecimal idxTupFetch) {
        this.idxTupFetch = idxTupFetch;
    }

    public BigDecimal getnTupIns() {
        return nTupIns;
    }

    public TableStat nTupIns(BigDecimal nTupIns) {
        this.nTupIns = nTupIns;
        return this;
    }

    public void setnTupIns(BigDecimal nTupIns) {
        this.nTupIns = nTupIns;
    }

    public BigDecimal getnTupUpd() {
        return nTupUpd;
    }

    public TableStat nTupUpd(BigDecimal nTupUpd) {
        this.nTupUpd = nTupUpd;
        return this;
    }

    public void setnTupUpd(BigDecimal nTupUpd) {
        this.nTupUpd = nTupUpd;
    }

    public BigDecimal getnTupDel() {
        return nTupDel;
    }

    public TableStat nTupDel(BigDecimal nTupDel) {
        this.nTupDel = nTupDel;
        return this;
    }

    public void setnTupDel(BigDecimal nTupDel) {
        this.nTupDel = nTupDel;
    }

    public BigDecimal getnTupHotUpd() {
        return nTupHotUpd;
    }

    public TableStat nTupHotUpd(BigDecimal nTupHotUpd) {
        this.nTupHotUpd = nTupHotUpd;
        return this;
    }

    public void setnTupHotUpd(BigDecimal nTupHotUpd) {
        this.nTupHotUpd = nTupHotUpd;
    }

    public BigDecimal getnLiveTup() {
        return nLiveTup;
    }

    public TableStat nLiveTup(BigDecimal nLiveTup) {
        this.nLiveTup = nLiveTup;
        return this;
    }

    public void setnLiveTup(BigDecimal nLiveTup) {
        this.nLiveTup = nLiveTup;
    }

    public BigDecimal getnDeadTup() {
        return nDeadTup;
    }

    public TableStat nDeadTup(BigDecimal nDeadTup) {
        this.nDeadTup = nDeadTup;
        return this;
    }

    public void setnDeadTup(BigDecimal nDeadTup) {
        this.nDeadTup = nDeadTup;
    }

    public BigDecimal getVacuumCount() {
        return vacuumCount;
    }

    public TableStat vacuumCount(BigDecimal vacuumCount) {
        this.vacuumCount = vacuumCount;
        return this;
    }

    public void setVacuumCount(BigDecimal vacuumCount) {
        this.vacuumCount = vacuumCount;
    }

    public BigDecimal getAutovacuumCount() {
        return autovacuumCount;
    }

    public TableStat autovacuumCount(BigDecimal autovacuumCount) {
        this.autovacuumCount = autovacuumCount;
        return this;
    }

    public void setAutovacuumCount(BigDecimal autovacuumCount) {
        this.autovacuumCount = autovacuumCount;
    }

    public BigDecimal getAnalyzeCount() {
        return analyzeCount;
    }

    public TableStat analyzeCount(BigDecimal analyzeCount) {
        this.analyzeCount = analyzeCount;
        return this;
    }

    public void setAnalyzeCount(BigDecimal analyzeCount) {
        this.analyzeCount = analyzeCount;
    }

    public BigDecimal getAutoanalyzeCount() {
        return autoanalyzeCount;
    }

    public TableStat autoanalyzeCount(BigDecimal autoanalyzeCount) {
        this.autoanalyzeCount = autoanalyzeCount;
        return this;
    }

    public void setAutoanalyzeCount(BigDecimal autoanalyzeCount) {
        this.autoanalyzeCount = autoanalyzeCount;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public TableStat updateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateNumber() {
        return updateNumber;
    }

    public TableStat updateNumber(Integer updateNumber) {
        this.updateNumber = updateNumber;
        return this;
    }

    public void setUpdateNumber(Integer updateNumber) {
        this.updateNumber = updateNumber;
    }

    public LocalDate getLastVacuum() {
        return lastVacuum;
    }

    public TableStat lastVacuum(LocalDate lastVacuum) {
        this.lastVacuum = lastVacuum;
        return this;
    }

    public void setLastVacuum(LocalDate lastVacuum) {
        this.lastVacuum = lastVacuum;
    }

    public LocalDate getLastAutovacuum() {
        return lastAutovacuum;
    }

    public TableStat lastAutovacuum(LocalDate lastAutovacuum) {
        this.lastAutovacuum = lastAutovacuum;
        return this;
    }

    public void setLastAutovacuum(LocalDate lastAutovacuum) {
        this.lastAutovacuum = lastAutovacuum;
    }

    public LocalDate getLastAnalyze() {
        return lastAnalyze;
    }

    public TableStat lastAnalyze(LocalDate lastAnalyze) {
        this.lastAnalyze = lastAnalyze;
        return this;
    }

    public void setLastAnalyze(LocalDate lastAnalyze) {
        this.lastAnalyze = lastAnalyze;
    }

    public LocalDate getLastAutoanalyze() {
        return lastAutoanalyze;
    }

    public TableStat lastAutoanalyze(LocalDate lastAutoanalyze) {
        this.lastAutoanalyze = lastAutoanalyze;
        return this;
    }

    public void setLastAutoanalyze(LocalDate lastAutoanalyze) {
        this.lastAutoanalyze = lastAutoanalyze;
    }

    public System getSystem() {
        return system;
    }

    public TableStat system(System system) {
        this.system = system;
        return this;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableStat tableStat = (TableStat) o;
        if (tableStat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tableStat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableStat{" +
            "id=" + getId() +
            ", schemaname='" + getSchemaname() + "'" +
            ", relname='" + getRelname() + "'" +
            ", seqScan='" + getSeqScan() + "'" +
            ", seqTupRead='" + getSeqTupRead() + "'" +
            ", idxScan='" + getIdxScan() + "'" +
            ", idxTupFetch='" + getIdxTupFetch() + "'" +
            ", nTupIns='" + getNTupIns() + "'" +
            ", nTupUpd='" + getNTupUpd() + "'" +
            ", nTupDel='" + getNTupDel() + "'" +
            ", nTupHotUpd='" + getNTupHotUpd() + "'" +
            ", nLiveTup='" + getNLiveTup() + "'" +
            ", nDeadTup='" + getNDeadTup() + "'" +
            ", vacuumCount='" + getVacuumCount() + "'" +
            ", autovacuumCount='" + getAutovacuumCount() + "'" +
            ", analyzeCount='" + getAnalyzeCount() + "'" +
            ", autoanalyzeCount='" + getAutoanalyzeCount() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateNumber='" + getUpdateNumber() + "'" +
            ", lastVacuum='" + getLastVacuum() + "'" +
            ", lastAutovacuum='" + getLastAutovacuum() + "'" +
            ", lastAnalyze='" + getLastAnalyze() + "'" +
            ", lastAutoanalyze='" + getLastAutoanalyze() + "'" +
            "}";
    }
}
