package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.AccountBalance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

}
