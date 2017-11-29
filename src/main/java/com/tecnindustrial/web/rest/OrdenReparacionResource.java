package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.OrdenReparacion;

import com.tecnindustrial.repository.OrdenReparacionRepository;
import com.tecnindustrial.web.rest.errors.BadRequestAlertException;
import com.tecnindustrial.web.rest.util.HeaderUtil;
import com.tecnindustrial.web.rest.util.PaginationUtil;
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
 * REST controller for managing OrdenReparacion.
 */
@RestController
@RequestMapping("/api")
public class OrdenReparacionResource {

    private final Logger log = LoggerFactory.getLogger(OrdenReparacionResource.class);

    private static final String ENTITY_NAME = "ordenReparacion";

    private final OrdenReparacionRepository ordenReparacionRepository;

    public OrdenReparacionResource(OrdenReparacionRepository ordenReparacionRepository) {
        this.ordenReparacionRepository = ordenReparacionRepository;
    }

    /**
     * POST  /orden-reparacions : Create a new ordenReparacion.
     *
     * @param ordenReparacion the ordenReparacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenReparacion, or with status 400 (Bad Request) if the ordenReparacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orden-reparacions")
    @Timed
    public ResponseEntity<OrdenReparacion> createOrdenReparacion(@Valid @RequestBody OrdenReparacion ordenReparacion) throws URISyntaxException {
        log.debug("REST request to save OrdenReparacion : {}", ordenReparacion);
        if (ordenReparacion.getId() != null) {
            throw new BadRequestAlertException("A new ordenReparacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdenReparacion result = ordenReparacionRepository.save(ordenReparacion);
        return ResponseEntity.created(new URI("/api/orden-reparacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orden-reparacions : Updates an existing ordenReparacion.
     *
     * @param ordenReparacion the ordenReparacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenReparacion,
     * or with status 400 (Bad Request) if the ordenReparacion is not valid,
     * or with status 500 (Internal Server Error) if the ordenReparacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orden-reparacions")
    @Timed
    public ResponseEntity<OrdenReparacion> updateOrdenReparacion(@Valid @RequestBody OrdenReparacion ordenReparacion) throws URISyntaxException {
        log.debug("REST request to update OrdenReparacion : {}", ordenReparacion);
        if (ordenReparacion.getId() == null) {
            return createOrdenReparacion(ordenReparacion);
        }
        OrdenReparacion result = ordenReparacionRepository.save(ordenReparacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenReparacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orden-reparacions : get all the ordenReparacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordenReparacions in body
     */
    @GetMapping("/orden-reparacions")
    @Timed
    public ResponseEntity<List<OrdenReparacion>> getAllOrdenReparacions(Pageable pageable) {
        log.debug("REST request to get a page of OrdenReparacions");
        Page<OrdenReparacion> page = ordenReparacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orden-reparacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orden-reparacions/:id : get the "id" ordenReparacion.
     *
     * @param id the id of the ordenReparacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenReparacion, or with status 404 (Not Found)
     */
    @GetMapping("/orden-reparacions/{id}")
    @Timed
    public ResponseEntity<OrdenReparacion> getOrdenReparacion(@PathVariable Long id) {
        log.debug("REST request to get OrdenReparacion : {}", id);
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordenReparacion));
    }

    /**
     * DELETE  /orden-reparacions/:id : delete the "id" ordenReparacion.
     *
     * @param id the id of the ordenReparacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orden-reparacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdenReparacion(@PathVariable Long id) {
        log.debug("REST request to delete OrdenReparacion : {}", id);
        ordenReparacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
