package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.CustomerBlacklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerBlacklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerBlacklistRepository extends JpaRepository<CustomerBlacklist, Long> {

}
