package com.electron.mfs.pg.services.repository;

import com.electron.mfs.pg.services.domain.Connector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Connector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {

}
