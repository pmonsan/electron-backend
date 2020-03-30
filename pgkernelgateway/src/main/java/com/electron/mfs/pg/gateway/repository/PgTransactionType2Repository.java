package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgTransactionType2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgTransactionType2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgTransactionType2Repository extends JpaRepository<PgTransactionType2, Long> {

}
