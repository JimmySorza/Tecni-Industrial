package com.tecnindustrial.repository;

import com.tecnindustrial.domain.VentaLinea;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VentaLinea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaLineaRepository extends JpaRepository<VentaLinea, Long> {

}
