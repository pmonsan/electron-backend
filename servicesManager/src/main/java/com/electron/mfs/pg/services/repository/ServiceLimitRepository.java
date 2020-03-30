package com.electron.mfs.pg.services.repository;

import com.electron.mfs.pg.services.domain.ServiceLimit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceLimit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceLimitRepository extends JpaRepository<ServiceLimit, Long> {

}
