package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.DetailContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailContractRepository extends JpaRepository<DetailContract, Long> {

}
