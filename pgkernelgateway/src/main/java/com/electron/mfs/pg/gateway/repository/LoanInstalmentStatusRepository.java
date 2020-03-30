package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.LoanInstalmentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanInstalmentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanInstalmentStatusRepository extends JpaRepository<LoanInstalmentStatus, Long> {

}
