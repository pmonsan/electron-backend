package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.PartnerType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartnerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerTypeRepository extends JpaRepository<PartnerType, Long> {

}
