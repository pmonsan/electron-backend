package com.electron.mfs.pg.account.repository;

import com.electron.mfs.pg.account.domain.AccountBalance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

}
