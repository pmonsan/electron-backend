package com.electron.mfs.pg.services.repository;

import com.electron.mfs.pg.services.domain.ConnectorType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConnectorType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectorTypeRepository extends JpaRepository<ConnectorType, Long> {

}
