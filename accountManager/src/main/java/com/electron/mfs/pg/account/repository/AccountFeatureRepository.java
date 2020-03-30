package com.electron.mfs.pg.account.repository;

import com.electron.mfs.pg.account.domain.AccountFeature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountFeature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountFeatureRepository extends JpaRepository<AccountFeature, Long> {

}
