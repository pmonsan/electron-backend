package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.PgData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgDataRepository extends JpaRepository<PgData, Long> {

}
