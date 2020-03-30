package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgBatchRepository extends JpaRepository<PgBatch, Long> {

}
