package com.electron.mfs.pg.account.repository;

import com.electron.mfs.pg.account.domain.ContractOpposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContractOpposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractOppositionRepository extends JpaRepository<ContractOpposition, Long> {

}
