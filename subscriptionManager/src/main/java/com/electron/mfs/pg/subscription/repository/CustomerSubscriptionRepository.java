package com.electron.mfs.pg.subscription.repository;

import com.electron.mfs.pg.subscription.domain.CustomerSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerSubscriptionRepository extends JpaRepository<CustomerSubscription, Long> {

}
