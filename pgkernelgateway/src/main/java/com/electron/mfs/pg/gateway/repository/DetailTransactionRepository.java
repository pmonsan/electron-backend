package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.DetailTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailTransactionRepository extends JpaRepository<DetailTransaction, Long> {

}
