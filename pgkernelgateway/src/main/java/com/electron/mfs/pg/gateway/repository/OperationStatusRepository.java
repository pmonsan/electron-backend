package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.OperationStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OperationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationStatusRepository extends JpaRepository<OperationStatus, Long> {

}
