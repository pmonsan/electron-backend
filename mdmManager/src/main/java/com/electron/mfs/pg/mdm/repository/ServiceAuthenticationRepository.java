package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.ServiceAuthentication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceAuthentication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAuthenticationRepository extends JpaRepository<ServiceAuthentication, Long> {

}
