package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.TransactionPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionPriceRepository extends JpaRepository<TransactionPrice, Long> {

}
