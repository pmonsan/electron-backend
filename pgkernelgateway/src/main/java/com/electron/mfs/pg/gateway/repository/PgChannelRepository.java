package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.PgChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgChannelRepository extends JpaRepository<PgChannel, Long> {

}
