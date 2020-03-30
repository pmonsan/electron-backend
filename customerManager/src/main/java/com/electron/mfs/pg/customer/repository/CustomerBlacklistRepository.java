package com.electron.mfs.pg.customer.repository;

import com.electron.mfs.pg.customer.domain.CustomerBlacklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerBlacklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerBlacklistRepository extends JpaRepository<CustomerBlacklist, Long> {

}
