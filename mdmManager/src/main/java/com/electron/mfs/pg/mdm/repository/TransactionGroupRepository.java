package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.TransactionGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionGroupRepository extends JpaRepository<TransactionGroup, Long> {

}
