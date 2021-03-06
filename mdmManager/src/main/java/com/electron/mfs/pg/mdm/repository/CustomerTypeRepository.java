package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.CustomerType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

}
