package com.electron.mfs.pg.subscription.repository;

import com.electron.mfs.pg.subscription.domain.ServiceIntegration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceIntegration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceIntegrationRepository extends JpaRepository<ServiceIntegration, Long> {

}
