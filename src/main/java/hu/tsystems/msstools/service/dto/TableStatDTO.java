package hu.tsystems.msstools.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TableStat entity.
 */
public class TableStatDTO implements Serializable {

    private Long id;

    private String schemaname;

    private String relname;

    private BigDecimal seqScan;

    private BigDecimal seqTupRead;

    private BigDecimal idxScan;

    private BigDecimal idxTupFetch;

    private BigDecimal nTupIns;

    private BigDecimal nTupUpd;

    private BigDecimal nTupDel;

    private BigDecimal nTupHotUpd;

    private BigDecimal nLiveTup;

    private BigDecimal nDeadTup;

    private BigDecimal vacuumCount;

    private BigDecimal autovacuumCount;

    private BigDecimal analyzeCount;

    private BigDecimal autoanalyzeCount;

    private LocalDate updateTime;

    private Integer updateNumber;

    private LocalDate lastVacuum;

    private LocalDate lastAutovacuum;

    private LocalDate lastAnalyze;

    private LocalDate lastAutoanalyze;

    private Long systemId;

    private String systemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public BigDecimal getSeqScan() {
        return seqScan;
    }

    public void setSeqScan(BigDecimal seqScan) {
        this.seqScan = seqScan;
    }

    public BigDecimal getSeqTupRead() {
        return seqTupRead;
    }

    public void setSeqTupRead(BigDecimal seqTupRead) {
        this.seqTupRead = seqTupRead;
    }

    public BigDecimal getIdxScan() {
        return idxScan;
    }

    public void setIdxScan(BigDecimal idxScan) {
        this.idxScan = idxScan;
    }

    public BigDecimal getIdxTupFetch() {
        return idxTupFetch;
    }

    public void setIdxTupFetch(BigDecimal idxTupFetch) {
        this.idxTupFetch = idxTupFetch;
    }

    public BigDecimal getnTupIns() {
        return nTupIns;
    }

    public void setnTupIns(BigDecimal nTupIns) {
        this.nTupIns = nTupIns;
    }

    public BigDecimal getnTupUpd() {
        return nTupUpd;
    }

    public void setnTupUpd(BigDecimal nTupUpd) {
        this.nTupUpd = nTupUpd;
    }

    public BigDecimal getnTupDel() {
        return nTupDel;
    }

    public void setnTupDel(BigDecimal nTupDel) {
        this.nTupDel = nTupDel;
    }

    public BigDecimal getnTupHotUpd() {
        return nTupHotUpd;
    }

    public void setnTupHotUpd(BigDecimal nTupHotUpd) {
        this.nTupHotUpd = nTupHotUpd;
    }

    public BigDecimal getnLiveTup() {
        return nLiveTup;
    }

    public void setnLiveTup(BigDecimal nLiveTup) {
        this.nLiveTup = nLiveTup;
    }

    public BigDecimal getnDeadTup() {
        return nDeadTup;
    }

    public void setnDeadTup(BigDecimal nDeadTup) {
        this.nDeadTup = nDeadTup;
    }

    public BigDecimal getVacuumCount() {
        return vacuumCount;
    }

    public void setVacuumCount(BigDecimal vacuumCount) {
        this.vacuumCount = vacuumCount;
    }

    public BigDecimal getAutovacuumCount() {
        return autovacuumCount;
    }

    public void setAutovacuumCount(BigDecimal autovacuumCount) {
        this.autovacuumCount = autovacuumCount;
    }

    public BigDecimal getAnalyzeCount() {
        return analyzeCount;
    }

    public void setAnalyzeCount(BigDecimal analyzeCount) {
        this.analyzeCount = analyzeCount;
    }

    public BigDecimal getAutoanalyzeCount() {
        return autoanalyzeCount;
    }

    public void setAutoanalyzeCount(BigDecimal autoanalyzeCount) {
        this.autoanalyzeCount = autoanalyzeCount;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(Integer updateNumber) {
        this.updateNumber = updateNumber;
    }

    public LocalDate getLastVacuum() {
        return lastVacuum;
    }

    public void setLastVacuum(LocalDate lastVacuum) {
        this.lastVacuum = lastVacuum;
    }

    public LocalDate getLastAutovacuum() {
        return lastAutovacuum;
    }

    public void setLastAutovacuum(LocalDate lastAutovacuum) {
        this.lastAutovacuum = lastAutovacuum;
    }

    public LocalDate getLastAnalyze() {
        return lastAnalyze;
    }

    public void setLastAnalyze(LocalDate lastAnalyze) {
        this.lastAnalyze = lastAnalyze;
    }

    public LocalDate getLastAutoanalyze() {
        return lastAutoanalyze;
    }

    public void setLastAutoanalyze(LocalDate lastAutoanalyze) {
        this.lastAutoanalyze = lastAutoanalyze;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableStatDTO tableStatDTO = (TableStatDTO) o;
        if(tableStatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tableStatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableStatDTO{" +
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
