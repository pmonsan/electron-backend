package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgChannelAuthorized;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgChannelAuthorized entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgChannelAuthorizedRepository extends JpaRepository<PgChannelAuthorized, Long> {

}
