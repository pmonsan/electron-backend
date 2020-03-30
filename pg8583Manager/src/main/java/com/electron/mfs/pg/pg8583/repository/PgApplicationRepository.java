package com.electron.mfs.pg.pg8583.repository;

import com.electron.mfs.pg.pg8583.domain.PgApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgApplicationRepository extends JpaRepository<PgApplication, Long> {

}
