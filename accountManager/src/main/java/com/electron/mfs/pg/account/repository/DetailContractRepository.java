package com.electron.mfs.pg.account.repository;

import com.electron.mfs.pg.account.domain.DetailContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailContractRepository extends JpaRepository<DetailContract, Long> {

}
