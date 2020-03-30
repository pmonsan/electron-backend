package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgTransactionType1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgTransactionType1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgTransactionType1Repository extends JpaRepository<PgTransactionType1, Long> {

}
