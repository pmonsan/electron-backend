package com.electron.mfs.pg.feescommission.repository;

import com.electron.mfs.pg.feescommission.domain.PricePlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PricePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricePlanRepository extends JpaRepository<PricePlan, Long> {

}
