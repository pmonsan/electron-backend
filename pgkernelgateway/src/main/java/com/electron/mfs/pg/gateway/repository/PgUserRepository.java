package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgUserRepository extends JpaRepository<PgUser, Long> {

}
