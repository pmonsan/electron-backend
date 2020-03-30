package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgMessageModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgMessageModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgMessageModelRepository extends JpaRepository<PgMessageModel, Long> {

}
