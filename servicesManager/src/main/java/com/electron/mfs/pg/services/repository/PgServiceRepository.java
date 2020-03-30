package com.electron.mfs.pg.services.repository;

import com.electron.mfs.pg.services.domain.PgService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgServiceRepository extends JpaRepository<PgService, Long> {

}
