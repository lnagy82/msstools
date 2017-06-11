package hu.tsystems.msstools.service;

import hu.tsystems.msstools.domain.TableStat;
import hu.tsystems.msstools.repository.TableStatRepository;
import hu.tsystems.msstools.service.dto.TableStatDTO;
import hu.tsystems.msstools.service.mapper.TableStatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TableStat.
 */
@Service
@Transactional
public class TableStatService {

    private final Logger log = LoggerFactory.getLogger(TableStatService.class);
    
    private final TableStatRepository tableStatRepository;

    private final TableStatMapper tableStatMapper;

    public TableStatService(TableStatRepository tableStatRepository, TableStatMapper tableStatMapper) {
        this.tableStatRepository = tableStatRepository;
        this.tableStatMapper = tableStatMapper;
    }

    /**
     * Save a tableStat.
     *
     * @param tableStatDTO the entity to save
     * @return the persisted entity
     */
    public TableStatDTO save(TableStatDTO tableStatDTO) {
        log.debug("Request to save TableStat : {}", tableStatDTO);
        TableStat tableStat = tableStatMapper.toEntity(tableStatDTO);
        tableStat = tableStatRepository.save(tableStat);
        TableStatDTO result = tableStatMapper.toDto(tableStat);
        return result;
    }

    /**
     *  Get all the tableStats.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TableStatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TableStats");
        Page<TableStat> result = tableStatRepository.findAll(pageable);
        return result.map(tableStat -> tableStatMapper.toDto(tableStat));
    }

    /**
     *  Get one tableStat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TableStatDTO findOne(Long id) {
        log.debug("Request to get TableStat : {}", id);
        TableStat tableStat = tableStatRepository.findOne(id);
        TableStatDTO tableStatDTO = tableStatMapper.toDto(tableStat);
        return tableStatDTO;
    }

    /**
     *  Delete the  tableStat by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TableStat : {}", id);
        tableStatRepository.delete(id);
    }
}
