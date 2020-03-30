package com.electron.mfs.pg.transactions.repository;

import com.electron.mfs.pg.transactions.domain.TransactionCommission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionCommission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionCommissionRepository extends JpaRepository<TransactionCommission, Long> {

}
