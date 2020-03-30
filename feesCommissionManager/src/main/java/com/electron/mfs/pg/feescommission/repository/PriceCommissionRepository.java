package com.electron.mfs.pg.feescommission.repository;

import com.electron.mfs.pg.feescommission.domain.PriceCommission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceCommission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceCommissionRepository extends JpaRepository<PriceCommission, Long> {

}
