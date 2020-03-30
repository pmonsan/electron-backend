package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgResponseCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgResponseCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgResponseCodeRepository extends JpaRepository<PgResponseCode, Long> {

}
