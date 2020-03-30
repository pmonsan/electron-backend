package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.Forex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Forex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForexRepository extends JpaRepository<Forex, Long> {

}
