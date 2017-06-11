package hu.tsystems.msstools.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.tsystems.msstools.service.SystemService;
import hu.tsystems.msstools.web.rest.util.HeaderUtil;
import hu.tsystems.msstools.web.rest.util.PaginationUtil;
import hu.tsystems.msstools.service.dto.SystemAppDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing System.
 */
@RestController
@RequestMapping("/api")
public class SystemAppResource {

    private final Logger log = LoggerFactory.getLogger(SystemAppResource.class);

    private static final String ENTITY_NAME = "system";
        
    private final SystemService systemService;

    public SystemAppResource(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * POST  /systems : Create a new system.
     *
     * @param systemDTO the systemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemDTO, or with status 400 (Bad Request) if the system has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/systems")
    @Timed
    public ResponseEntity<SystemAppDTO> createSystem(@Valid @RequestBody SystemAppDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to save System : {}", systemDTO);
        if (systemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new system cannot already have an ID")).body(null);
        }
        SystemAppDTO result = systemService.save(systemDTO);
        return ResponseEntity.created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /systems : Updates an existing system.
     *
     * @param systemDTO the systemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemDTO,
     * or with status 400 (Bad Request) if the systemDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/systems")
    @Timed
    public ResponseEntity<SystemAppDTO> updateSystem(@Valid @RequestBody SystemAppDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to update System : {}", systemDTO);
        if (systemDTO.getId() == null) {
            return createSystem(systemDTO);
        }
        SystemAppDTO result = systemService.save(systemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /systems : get all the systems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of systems in body
     */
    @GetMapping("/systems")
    @Timed
    public ResponseEntity<List<SystemAppDTO>> getAllSystems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Systems");
        Page<SystemAppDTO> page = systemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/systems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /systems/:id : get the "id" system.
     *
     * @param id the id of the systemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/systems/{id}")
    @Timed
    public ResponseEntity<SystemAppDTO> getSystem(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        SystemAppDTO systemDTO = systemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(systemDTO));
    }

    /**
     * DELETE  /systems/:id : delete the "id" system.
     *
     * @param id the id of the systemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/systems/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        log.debug("REST request to delete System : {}", id);
        systemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
