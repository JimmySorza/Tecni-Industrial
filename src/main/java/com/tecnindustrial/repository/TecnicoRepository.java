package com.tecnindustrial.repository;

import com.tecnindustrial.domain.Tecnico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tecnico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

}
