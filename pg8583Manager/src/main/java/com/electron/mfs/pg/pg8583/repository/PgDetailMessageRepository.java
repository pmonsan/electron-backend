package com.electron.mfs.pg.pg8583.repository;

import com.electron.mfs.pg.pg8583.domain.PgDetailMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgDetailMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgDetailMessageRepository extends JpaRepository<PgDetailMessage, Long> {

}
