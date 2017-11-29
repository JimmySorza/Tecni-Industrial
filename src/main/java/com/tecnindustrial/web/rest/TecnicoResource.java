package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.Tecnico;

import com.tecnindustrial.repository.TecnicoRepository;
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
 * REST controller for managing Tecnico.
 */
@RestController
@RequestMapping("/api")
public class TecnicoResource {

    private final Logger log = LoggerFactory.getLogger(TecnicoResource.class);

    private static final String ENTITY_NAME = "tecnico";

    private final TecnicoRepository tecnicoRepository;

    public TecnicoResource(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    /**
     * POST  /tecnicos : Create a new tecnico.
     *
     * @param tecnico the tecnico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tecnico, or with status 400 (Bad Request) if the tecnico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tecnicos")
    @Timed
    public ResponseEntity<Tecnico> createTecnico(@RequestBody Tecnico tecnico) throws URISyntaxException {
        log.debug("REST request to save Tecnico : {}", tecnico);
        if (tecnico.getId() != null) {
            throw new BadRequestAlertException("A new tecnico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tecnico result = tecnicoRepository.save(tecnico);
        return ResponseEntity.created(new URI("/api/tecnicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tecnicos : Updates an existing tecnico.
     *
     * @param tecnico the tecnico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tecnico,
     * or with status 400 (Bad Request) if the tecnico is not valid,
     * or with status 500 (Internal Server Error) if the tecnico couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tecnicos")
    @Timed
    public ResponseEntity<Tecnico> updateTecnico(@RequestBody Tecnico tecnico) throws URISyntaxException {
        log.debug("REST request to update Tecnico : {}", tecnico);
        if (tecnico.getId() == null) {
            return createTecnico(tecnico);
        }
        Tecnico result = tecnicoRepository.save(tecnico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tecnico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tecnicos : get all the tecnicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tecnicos in body
     */
    @GetMapping("/tecnicos")
    @Timed
    public List<Tecnico> getAllTecnicos() {
        log.debug("REST request to get all Tecnicos");
        return tecnicoRepository.findAll();
        }

    /**
     * GET  /tecnicos/:id : get the "id" tecnico.
     *
     * @param id the id of the tecnico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tecnico, or with status 404 (Not Found)
     */
    @GetMapping("/tecnicos/{id}")
    @Timed
    public ResponseEntity<Tecnico> getTecnico(@PathVariable Long id) {
        log.debug("REST request to get Tecnico : {}", id);
        Tecnico tecnico = tecnicoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tecnico));
    }

    /**
     * DELETE  /tecnicos/:id : delete the "id" tecnico.
     *
     * @param id the id of the tecnico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tecnicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTecnico(@PathVariable Long id) {
        log.debug("REST request to delete Tecnico : {}", id);
        tecnicoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
