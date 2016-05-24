package com.fanniemae.icf.rw.repository;

import com.fanniemae.icf.rw.domain.RepAndWarrant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RepAndWarrant entity.
 */
@SuppressWarnings("unused")
public interface RepAndWarrantRepository extends JpaRepository<RepAndWarrant,Long> {

}
