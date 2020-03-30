package com.electron.mfs.pg.subscription.repository;

import com.electron.mfs.pg.subscription.domain.PartnerSecurity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartnerSecurity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerSecurityRepository extends JpaRepository<PartnerSecurity, Long> {

}
