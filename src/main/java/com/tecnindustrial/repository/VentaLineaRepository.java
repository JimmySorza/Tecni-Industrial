package com.tecnindustrial.repository;

import com.tecnindustrial.domain.VentaLinea;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the VentaLinea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaLineaRepository extends JpaRepository<VentaLinea, Long> {

    List<VentaLinea> findByVentaId(Long Id);
}
