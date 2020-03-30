package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.BeneficiaryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BeneficiaryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaryTypeRepository extends JpaRepository<BeneficiaryType, Long> {

}
