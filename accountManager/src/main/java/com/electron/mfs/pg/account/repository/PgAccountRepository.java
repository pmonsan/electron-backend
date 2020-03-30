package com.electron.mfs.pg.account.repository;

import com.electron.mfs.pg.account.domain.PgAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PgAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgAccountRepository extends JpaRepository<PgAccount, Long> {

}
