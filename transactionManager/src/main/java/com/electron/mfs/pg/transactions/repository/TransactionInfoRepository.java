package com.electron.mfs.pg.transactions.repository;

import com.electron.mfs.pg.transactions.domain.TransactionInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {

}
