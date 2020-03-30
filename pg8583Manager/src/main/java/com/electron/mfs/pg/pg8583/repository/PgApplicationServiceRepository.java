package com.electron.mfs.pg.pg8583.repository;

import com.electron.mfs.pg.pg8583.domain.PgApplicationService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgApplicationService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgApplicationServiceRepository extends JpaRepository<PgApplicationService, Long> {

}
