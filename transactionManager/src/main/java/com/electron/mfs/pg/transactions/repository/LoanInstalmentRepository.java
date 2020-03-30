package com.electron.mfs.pg.transactions.repository;

import com.electron.mfs.pg.transactions.domain.LoanInstalment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanInstalment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanInstalmentRepository extends JpaRepository<LoanInstalment, Long> {

}
