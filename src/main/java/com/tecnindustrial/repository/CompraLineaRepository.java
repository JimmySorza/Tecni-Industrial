package com.tecnindustrial.repository;

import com.tecnindustrial.domain.CompraLinea;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CompraLinea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraLineaRepository extends JpaRepository<CompraLinea, Long> {
    List<CompraLinea> findByCompraId(Long Id);

}
