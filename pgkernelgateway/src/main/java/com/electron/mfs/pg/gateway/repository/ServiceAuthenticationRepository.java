package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.ServiceAuthentication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceAuthentication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAuthenticationRepository extends JpaRepository<ServiceAuthentication, Long> {

}
