package com.electron.mfs.pg.gateway.repository;

import com.electron.mfs.pg.gateway.domain.TransactionChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionChannelRepository extends JpaRepository<TransactionChannel, Long> {

}
