package com.electron.mfs.pg.transactions.repository;

import com.electron.mfs.pg.transactions.domain.DetailTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailTransactionRepository extends JpaRepository<DetailTransaction, Long> {

}
