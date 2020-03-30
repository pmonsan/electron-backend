package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.AccountStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

}
