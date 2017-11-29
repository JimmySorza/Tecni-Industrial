package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.OrdenLinea;

import com.tecnindustrial.repository.OrdenLineaRepository;
import com.tecnindustrial.web.rest.errors.BadRequestAlertException;
import com.tecnindustrial.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrdenLinea.
 */
@RestController
@RequestMapping("/api")
public class OrdenLineaResource {

    private final Logger log = LoggerFactory.getLogger(OrdenLineaResource.class);

    private static final String ENTITY_NAME = "ordenLinea";

    private final OrdenLineaRepository ordenLineaRepository;

    public OrdenLineaResource(OrdenLineaRepository ordenLineaRepository) {
        this.ordenLineaRepository = ordenLineaRepository;
    }

    /**
     * POST  /orden-lineas : Create a new ordenLinea.
     *
     * @param ordenLinea the ordenLinea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenLinea, or with status 400 (Bad Request) if the ordenLinea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orden-lineas")
    @Timed
    public ResponseEntity<OrdenLinea> createOrdenLinea(@RequestBody OrdenLinea ordenLinea) throws URISyntaxException {
        log.debug("REST request to save OrdenLinea : {}", ordenLinea);
        if (ordenLinea.getId() != null) {
            throw new BadRequestAlertException("A new ordenLinea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdenLinea result = ordenLineaRepository.save(ordenLinea);
        return ResponseEntity.created(new URI("/api/orden-lineas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orden-lineas : Updates an existing ordenLinea.
     *
     * @param ordenLinea the ordenLinea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenLinea,
     * or with status 400 (Bad Request) if the ordenLinea is not valid,
     * or with status 500 (Internal Server Error) if the ordenLinea couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orden-lineas")
    @Timed
    public ResponseEntity<OrdenLinea> updateOrdenLinea(@RequestBody OrdenLinea ordenLinea) throws URISyntaxException {
        log.debug("REST request to update OrdenLinea : {}", ordenLinea);
        if (ordenLinea.getId() == null) {
            return createOrdenLinea(ordenLinea);
        }
        OrdenLinea result = ordenLineaRepository.save(ordenLinea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenLinea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orden-lineas : get all the ordenLineas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordenLineas in body
     */
    @GetMapping("/orden-lineas")
    @Timed
    public List<OrdenLinea> getAllOrdenLineas() {
        log.debug("REST request to get all OrdenLineas");
        return ordenLineaRepository.findAll();
        }

    /**
     * GET  /orden-lineas/:id : get the "id" ordenLinea.
     *
     * @param id the id of the ordenLinea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenLinea, or with status 404 (Not Found)
     */
    @GetMapping("/orden-lineas/{id}")
    @Timed
    public ResponseEntity<OrdenLinea> getOrdenLinea(@PathVariable Long id) {
        log.debug("REST request to get OrdenLinea : {}", id);
        OrdenLinea ordenLinea = ordenLineaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordenLinea));
    }

    /**
     * DELETE  /orden-lineas/:id : delete the "id" ordenLinea.
     *
     * @param id the id of the ordenLinea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orden-lineas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdenLinea(@PathVariable Long id) {
        log.debug("REST request to delete OrdenLinea : {}", id);
        ordenLineaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
