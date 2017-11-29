package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.VentaLinea;

import com.tecnindustrial.repository.VentaLineaRepository;
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
 * REST controller for managing VentaLinea.
 */
@RestController
@RequestMapping("/api")
public class VentaLineaResource {

    private final Logger log = LoggerFactory.getLogger(VentaLineaResource.class);

    private static final String ENTITY_NAME = "ventaLinea";

    private final VentaLineaRepository ventaLineaRepository;

    public VentaLineaResource(VentaLineaRepository ventaLineaRepository) {
        this.ventaLineaRepository = ventaLineaRepository;
    }

    /**
     * POST  /venta-lineas : Create a new ventaLinea.
     *
     * @param ventaLinea the ventaLinea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ventaLinea, or with status 400 (Bad Request) if the ventaLinea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/venta-lineas")
    @Timed
    public ResponseEntity<VentaLinea> createVentaLinea(@RequestBody VentaLinea ventaLinea) throws URISyntaxException {
        log.debug("REST request to save VentaLinea : {}", ventaLinea);
        if (ventaLinea.getId() != null) {
            throw new BadRequestAlertException("A new ventaLinea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaLinea result = ventaLineaRepository.save(ventaLinea);
        return ResponseEntity.created(new URI("/api/venta-lineas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /venta-lineas : Updates an existing ventaLinea.
     *
     * @param ventaLinea the ventaLinea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ventaLinea,
     * or with status 400 (Bad Request) if the ventaLinea is not valid,
     * or with status 500 (Internal Server Error) if the ventaLinea couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/venta-lineas")
    @Timed
    public ResponseEntity<VentaLinea> updateVentaLinea(@RequestBody VentaLinea ventaLinea) throws URISyntaxException {
        log.debug("REST request to update VentaLinea : {}", ventaLinea);
        if (ventaLinea.getId() == null) {
            return createVentaLinea(ventaLinea);
        }
        VentaLinea result = ventaLineaRepository.save(ventaLinea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ventaLinea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /venta-lineas : get all the ventaLineas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ventaLineas in body
     */
    @GetMapping("/venta-lineas")
    @Timed
    public List<VentaLinea> getAllVentaLineas() {
        log.debug("REST request to get all VentaLineas");
        return ventaLineaRepository.findAll();
        }

    /**
     * GET  /venta-lineas/:id : get the "id" ventaLinea.
     *
     * @param id the id of the ventaLinea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ventaLinea, or with status 404 (Not Found)
     */
    @GetMapping("/venta-lineas/{id}")
    @Timed
    public ResponseEntity<VentaLinea> getVentaLinea(@PathVariable Long id) {
        log.debug("REST request to get VentaLinea : {}", id);
        VentaLinea ventaLinea = ventaLineaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ventaLinea));
    }

    /**
     * GET  /venta-lineas/ventas/{ventaId} : get all the ventaLineas by venta id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in body
     */
    @GetMapping("/venta-lineas/ventas/{ventaId}")
    @Timed
    public List<VentaLinea> getAllVentaLineaForVenta(@PathVariable long ventaId){
        log.debug("REST request to get all VentaLineas for venta: {}", ventaId);

        List<VentaLinea> actions = ventaLineaRepository.findByVentaId(ventaId);
        return actions;
    }

    /**
     * DELETE  /venta-lineas/:id : delete the "id" ventaLinea.
     *
     * @param id the id of the ventaLinea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/venta-lineas/{id}")
    @Timed
    public ResponseEntity<Void> deleteVentaLinea(@PathVariable Long id) {
        log.debug("REST request to delete VentaLinea : {}", id);
        ventaLineaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
