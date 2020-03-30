package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.MeansofpaymentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MeansofpaymentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeansofpaymentTypeRepository extends JpaRepository<MeansofpaymentType, Long> {

}
