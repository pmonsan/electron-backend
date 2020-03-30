package com.electron.mfs.pg.feescommission.repository;

import com.electron.mfs.pg.feescommission.domain.SubscriptionPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubscriptionPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionPriceRepository extends JpaRepository<SubscriptionPrice, Long> {

}
