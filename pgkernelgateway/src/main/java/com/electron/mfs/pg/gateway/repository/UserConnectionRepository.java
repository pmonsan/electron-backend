package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.UserConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

}
