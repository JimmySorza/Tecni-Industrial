package com.tecnindustrial.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tecnindustrial.domain.CompraLinea;

import com.tecnindustrial.domain.Producto;
import com.tecnindustrial.repository.CompraLineaRepository;
import com.tecnindustrial.repository.ProductoRepository;
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
 * REST controller for managing CompraLinea.
 */
@RestController
@RequestMapping("/api")
public class CompraLineaResource {

    private final Logger log = LoggerFactory.getLogger(CompraLineaResource.class);

    private static final String ENTITY_NAME = "compraLinea";

    private final CompraLineaRepository compraLineaRepository;

    private final ProductoRepository productoRepository;

    public CompraLineaResource(CompraLineaRepository compraLineaRepository, ProductoRepository productoRepository) {
        this.compraLineaRepository = compraLineaRepository;
        this.productoRepository=productoRepository;
    }

    /**
     * POST  /compra-lineas : Create a new compraLinea.
     *
     * @param compraLinea the compraLinea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compraLinea, or with status 400 (Bad Request) if the compraLinea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compra-lineas")
    @Timed
    public ResponseEntity<CompraLinea> createCompraLinea(@RequestBody CompraLinea compraLinea) throws URISyntaxException {
        log.debug("REST request to save CompraLinea : {}", compraLinea);
        if (compraLinea.getId() != null) {
            throw new BadRequestAlertException("A new compraLinea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraLinea result = compraLineaRepository.save(compraLinea);
        return ResponseEntity.created(new URI("/api/compra-lineas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /** Jimmy
     * POST  /compralineas : Create a new compraLinea.
     *
     * @param compraLinea the compraLinea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compraLinea, or with status 400 (Bad Request) if the compraLinea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compralineas")
    @Timed
    public ResponseEntity<CompraLinea> createCompraLineas(@RequestBody CompraLinea compraLinea) throws URISyntaxException {
        log.debug("REST request to save CompraLinea : {}", compraLinea);
        if (compraLinea.getId() != null) {
            throw new BadRequestAlertException("A new compraLinea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraLinea result = compraLineaRepository.save(compraLinea);
        return ResponseEntity.created(new URI("/api/compralineas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compra-lineas : Updates an existing compraLinea.
     *
     * @param compraLinea the compraLinea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compraLinea,
     * or with status 400 (Bad Request) if the compraLinea is not valid,
     * or with status 500 (Internal Server Error) if the compraLinea couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compra-lineas")
    @Timed
    public ResponseEntity<CompraLinea> updateCompraLinea(@RequestBody CompraLinea compraLinea) throws URISyntaxException {
        log.debug("REST request to update CompraLinea : {}", compraLinea);
        if (compraLinea.getId() == null) {
            return createCompraLinea(compraLinea);
        }
        CompraLinea result = compraLineaRepository.save(compraLinea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compraLinea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compra-lineas : get all the compraLineas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compraLineas in body
     */
    @GetMapping("/compra-lineas")
    @Timed
    public List<CompraLinea> getAllCompraLineas() {
        log.debug("REST request to get all CompraLineas");
        return compraLineaRepository.findAll();
        }

    /**
     * GET  /compra-lineas/:id : get the "id" compraLinea.
     *
     * @param id the id of the compraLinea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compraLinea, or with status 404 (Not Found)
     */
    @GetMapping("/compra-lineas/{id}")
    @Timed
    public ResponseEntity<CompraLinea> getCompraLinea(@PathVariable Long id) {
        log.debug("REST request to get CompraLinea : {}", id);
        CompraLinea compraLinea = compraLineaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compraLinea));
    }

    /**
     * GET  /compra-lineas/compras/{compraId} : get all the compraLineas by compra id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in body
     */
    @GetMapping("/compra-lineas/compras/{compraId}")
    @Timed
    public List<CompraLinea> getAllCompraLineasForCompra(@PathVariable Long compraId){
        log.debug("REST request to get all CompraLineas for compra: {}", compraId);

        List<CompraLinea> actions = compraLineaRepository.findByCompraId(compraId) ;
        return actions;
    }


    /**
     * DELETE  /compra-lineas/:id : delete the "id" compraLinea.
     *
     * @param id the id of the compraLinea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compra-lineas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompraLinea(@PathVariable Long id) {
        log.debug("REST request to delete CompraLinea : {}", id);
        CompraLinea compraLinea = compraLineaRepository.findOne(id);
        Producto producto = compraLinea.getProducto();
        Long existencia = producto.getExistencia()-compraLinea.getCantidad();
        producto.setExistencia(existencia);
        productoRepository.save(producto);
        compraLineaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
