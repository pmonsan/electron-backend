package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.InternalConnectorStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InternalConnectorStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalConnectorStatusRepository extends JpaRepository<InternalConnectorStatus, Long> {

}
