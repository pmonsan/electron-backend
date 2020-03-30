package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.Connector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Connector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {

}
