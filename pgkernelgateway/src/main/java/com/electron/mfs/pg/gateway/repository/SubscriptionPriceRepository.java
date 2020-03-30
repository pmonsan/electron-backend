package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.SubscriptionPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubscriptionPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionPriceRepository extends JpaRepository<SubscriptionPrice, Long> {

}
