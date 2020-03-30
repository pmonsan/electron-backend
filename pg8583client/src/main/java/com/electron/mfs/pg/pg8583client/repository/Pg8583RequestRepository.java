package com.electron.mfs.pg.pg8583client.repository;

import com.electron.mfs.pg.pg8583client.domain.Pg8583Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pg8583Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Pg8583RequestRepository extends JpaRepository<Pg8583Request, Long> {

}
