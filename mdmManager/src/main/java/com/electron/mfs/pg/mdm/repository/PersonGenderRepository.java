package com.electron.mfs.pg.mdm.repository;

import com.electron.mfs.pg.mdm.domain.PersonGender;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonGender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonGenderRepository extends JpaRepository<PersonGender, Long> {

}
