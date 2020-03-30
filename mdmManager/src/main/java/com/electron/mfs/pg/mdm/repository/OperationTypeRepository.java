package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.OperationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OperationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

}
