package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgModuleRepository extends JpaRepository<PgModule, Long> {

}
