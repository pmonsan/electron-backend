package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.UserProfileData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserProfileData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileDataRepository extends JpaRepository<UserProfileData, Long> {

}
