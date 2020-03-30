package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.CustomerLimit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerLimit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerLimitRepository extends JpaRepository<CustomerLimit, Long> {

}
