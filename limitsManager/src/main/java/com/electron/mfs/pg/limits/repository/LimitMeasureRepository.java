package com.electron.mfs.pg.limits.repository;

import com.electron.mfs.pg.limits.domain.LimitMeasure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LimitMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LimitMeasureRepository extends JpaRepository<LimitMeasure, Long> {

}
