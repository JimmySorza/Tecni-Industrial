package com.tecnindustrial.repository;

import com.tecnindustrial.domain.OrdenReparacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrdenReparacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, Long> {

}
