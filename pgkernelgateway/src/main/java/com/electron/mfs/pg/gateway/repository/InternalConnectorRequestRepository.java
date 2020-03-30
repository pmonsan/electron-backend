package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.InternalConnectorRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InternalConnectorRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalConnectorRequestRepository extends JpaRepository<InternalConnectorRequest, Long> {

}
