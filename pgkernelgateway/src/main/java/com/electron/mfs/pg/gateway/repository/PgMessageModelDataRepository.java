package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgMessageModelData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgMessageModelData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgMessageModelDataRepository extends JpaRepository<PgMessageModelData, Long> {

}
