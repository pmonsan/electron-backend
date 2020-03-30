package com.electron.mfs.pg.pg8583.repository;

import com.electron.mfs.pg.pg8583.domain.PgMessageStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgMessageStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgMessageStatusRepository extends JpaRepository<PgMessageStatus, Long> {

}
