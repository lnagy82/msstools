package hu.tsystems.msstools.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.tsystems.msstools.service.TableStatService;
import hu.tsystems.msstools.web.rest.util.HeaderUtil;
import hu.tsystems.msstools.web.rest.util.PaginationUtil;
import hu.tsystems.msstools.service.dto.TableStatDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TableStat.
 */
@RestController
@RequestMapping("/api")
public class TableStatResource {

    private final Logger log = LoggerFactory.getLogger(TableStatResource.class);

    private static final String ENTITY_NAME = "tableStat";
        
    private final TableStatService tableStatService;

    public TableStatResource(TableStatService tableStatService) {
        this.tableStatService = tableStatService;
    }

    /**
     * POST  /table-stats : Create a new tableStat.
     *
     * @param tableStatDTO the tableStatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tableStatDTO, or with status 400 (Bad Request) if the tableStat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/table-stats")
    @Timed
    public ResponseEntity<TableStatDTO> createTableStat(@RequestBody TableStatDTO tableStatDTO) throws URISyntaxException {
        log.debug("REST request to save TableStat : {}", tableStatDTO);
        if (tableStatDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tableStat cannot already have an ID")).body(null);
        }
        TableStatDTO result = tableStatService.save(tableStatDTO);
        return ResponseEntity.created(new URI("/api/table-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-stats : Updates an existing tableStat.
     *
     * @param tableStatDTO the tableStatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tableStatDTO,
     * or with status 400 (Bad Request) if the tableStatDTO is not valid,
     * or with status 500 (Internal Server Error) if the tableStatDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/table-stats")
    @Timed
    public ResponseEntity<TableStatDTO> updateTableStat(@RequestBody TableStatDTO tableStatDTO) throws URISyntaxException {
        log.debug("REST request to update TableStat : {}", tableStatDTO);
        if (tableStatDTO.getId() == null) {
            return createTableStat(tableStatDTO);
        }
        TableStatDTO result = tableStatService.save(tableStatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tableStatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /table-stats : get all the tableStats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tableStats in body
     */
    @GetMapping("/table-stats")
    @Timed
    public ResponseEntity<List<TableStatDTO>> getAllTableStats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TableStats");
        Page<TableStatDTO> page = tableStatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/table-stats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /table-stats/:id : get the "id" tableStat.
     *
     * @param id the id of the tableStatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tableStatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/table-stats/{id}")
    @Timed
    public ResponseEntity<TableStatDTO> getTableStat(@PathVariable Long id) {
        log.debug("REST request to get TableStat : {}", id);
        TableStatDTO tableStatDTO = tableStatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tableStatDTO));
    }

    /**
     * DELETE  /table-stats/:id : delete the "id" tableStat.
     *
     * @param id the id of the tableStatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/table-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteTableStat(@PathVariable Long id) {
        log.debug("REST request to delete TableStat : {}", id);
        tableStatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
