package hu.tsystems.msstools.service;

import hu.tsystems.msstools.domain.System;
import hu.tsystems.msstools.repository.SystemRepository;
import hu.tsystems.msstools.service.dto.SystemDTO;
import hu.tsystems.msstools.service.mapper.SystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing System.
 */
@Service
@Transactional
public class SystemService {

    private final Logger log = LoggerFactory.getLogger(SystemService.class);
    
    private final SystemRepository systemRepository;

    private final SystemMapper systemMapper;

    public SystemService(SystemRepository systemRepository, SystemMapper systemMapper) {
        this.systemRepository = systemRepository;
        this.systemMapper = systemMapper;
    }

    /**
     * Save a system.
     *
     * @param systemDTO the entity to save
     * @return the persisted entity
     */
    public SystemDTO save(SystemDTO systemDTO) {
        log.debug("Request to save System : {}", systemDTO);
        System system = systemMapper.toEntity(systemDTO);
        system = systemRepository.save(system);
        SystemDTO result = systemMapper.toDto(system);
        return result;
    }

    /**
     *  Get all the systems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Systems");
        Page<System> result = systemRepository.findAll(pageable);
        return result.map(system -> systemMapper.toDto(system));
    }

    /**
     *  Get one system by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SystemDTO findOne(Long id) {
        log.debug("Request to get System : {}", id);
        System system = systemRepository.findOne(id);
        SystemDTO systemDTO = systemMapper.toDto(system);
        return systemDTO;
    }

    /**
     *  Delete the  system by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete System : {}", id);
        systemRepository.delete(id);
    }
}
