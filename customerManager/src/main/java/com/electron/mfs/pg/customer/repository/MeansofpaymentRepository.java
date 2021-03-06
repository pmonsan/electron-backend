package com.electron.mfs.pg.customer.repository;

import com.electron.mfs.pg.customer.domain.Meansofpayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Meansofpayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeansofpaymentRepository extends JpaRepository<Meansofpayment, Long> {

}
