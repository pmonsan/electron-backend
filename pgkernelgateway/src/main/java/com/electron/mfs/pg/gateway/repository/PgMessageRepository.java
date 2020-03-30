package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgMessageRepository extends JpaRepository<PgMessage, Long> {

}
