package com.electron.mfs.pg.iam.repository;

import com.electron.mfs.pg.iam.domain.UserConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

}
