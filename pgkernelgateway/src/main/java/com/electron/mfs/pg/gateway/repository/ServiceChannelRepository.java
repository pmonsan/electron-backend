package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.ServiceChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceChannelRepository extends JpaRepository<ServiceChannel, Long> {

}
