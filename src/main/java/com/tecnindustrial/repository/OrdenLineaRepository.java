package com.tecnindustrial.repository;

import com.tecnindustrial.domain.OrdenLinea;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrdenLinea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenLineaRepository extends JpaRepository<OrdenLinea, Long> {

}
