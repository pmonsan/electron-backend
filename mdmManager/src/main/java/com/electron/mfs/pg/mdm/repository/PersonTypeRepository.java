package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.PersonType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonTypeRepository extends JpaRepository<PersonType, Long> {

}
