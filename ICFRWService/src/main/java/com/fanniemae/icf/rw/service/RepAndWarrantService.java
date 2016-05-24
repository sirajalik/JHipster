package com.fanniemae.icf.rw.service;

import com.fanniemae.icf.rw.domain.RepAndWarrant;

import java.util.List;

/**
 * Service Interface for managing RepAndWarrant.
 */
public interface RepAndWarrantService {

    /**
     * Save a repAndWarrant.
     * 
     * @param repAndWarrant the entity to save
     * @return the persisted entity
     */
    RepAndWarrant save(RepAndWarrant repAndWarrant);

    /**
     *  Get all the repAndWarrants.
     *  
     *  @return the list of entities
     */
    List<RepAndWarrant> findAll();

    /**
     *  Get the "id" repAndWarrant.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    RepAndWarrant findOne(Long id);

    /**
     *  Delete the "id" repAndWarrant.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
