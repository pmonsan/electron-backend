package com.electron.mfs.pg.iam.repository;

import com.electron.mfs.pg.iam.domain.UserProfileData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserProfileData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileDataRepository extends JpaRepository<UserProfileData, Long> {

}
