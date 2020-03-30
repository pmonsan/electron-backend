package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.TransactionProperty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionPropertyRepository extends JpaRepository<TransactionProperty, Long> {

}
