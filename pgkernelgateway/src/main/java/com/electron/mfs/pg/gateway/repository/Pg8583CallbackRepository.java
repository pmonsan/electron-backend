package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.Pg8583Callback;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pg8583Callback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Pg8583CallbackRepository extends JpaRepository<Pg8583Callback, Long> {

}
