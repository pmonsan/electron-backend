package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.BeneficiaryRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BeneficiaryRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaryRelationshipRepository extends JpaRepository<BeneficiaryRelationship, Long> {

}
