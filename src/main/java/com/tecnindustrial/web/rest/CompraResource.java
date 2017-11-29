package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.Compra;

import com.tecnindustrial.repository.CompraRepository;
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
 * REST controller for managing Compra.
 */
@RestController
@RequestMapping("/api")
public class CompraResource {

    private final Logger log = LoggerFactory.getLogger(CompraResource.class);

    private static final String ENTITY_NAME = "compra";

    private final CompraRepository compraRepository;

    public CompraResource(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    /**
     * POST  /compras : Create a new compra.
     *
     * @param compra the compra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compra, or with status 400 (Bad Request) if the compra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compras")
    @Timed
    public ResponseEntity<Compra> createCompra(@RequestBody Compra compra) throws URISyntaxException {
        log.debug("REST request to save Compra : {}", compra);
        if (compra.getId() != null) {
            throw new BadRequestAlertException("A new compra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compra result = compraRepository.save(compra);
        return ResponseEntity.created(new URI("/api/compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compras : Updates an existing compra.
     *
     * @param compra the compra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compra,
     * or with status 400 (Bad Request) if the compra is not valid,
     * or with status 500 (Internal Server Error) if the compra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compras")
    @Timed
    public ResponseEntity<Compra> updateCompra(@RequestBody Compra compra) throws URISyntaxException {
        log.debug("REST request to update Compra : {}", compra);
        if (compra.getId() == null) {
            return createCompra(compra);
        }
        Compra result = compraRepository.save(compra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compras : get all the compras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compras in body
     */
    @GetMapping("/compras")
    @Timed
    public List<Compra> getAllCompras() {
        log.debug("REST request to get all Compras");
        return compraRepository.findAll();
        }

    /**
     * GET  /compras/:id : get the "id" compra.
     *
     * @param id the id of the compra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compra, or with status 404 (Not Found)
     */
    @GetMapping("/compras/{id}")
    @Timed
    public ResponseEntity<Compra> getCompra(@PathVariable Long id) {
        log.debug("REST request to get Compra : {}", id);
        Compra compra = compraRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compra));
    }

    /**
     * DELETE  /compras/:id : delete the "id" compra.
     *
     * @param id the id of the compra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        log.debug("REST request to delete Compra : {}", id);
        compraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
