package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.Periodicity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Periodicity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicityRepository extends JpaRepository<Periodicity, Long> {

}
