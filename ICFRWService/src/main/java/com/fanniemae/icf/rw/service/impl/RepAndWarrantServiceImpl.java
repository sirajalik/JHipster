package com.fanniemae.icf.rw.service.impl;

import com.fanniemae.icf.rw.service.RepAndWarrantService;
import com.fanniemae.icf.rw.domain.RepAndWarrant;
import com.fanniemae.icf.rw.repository.RepAndWarrantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing RepAndWarrant.
 */
@Service
@Transactional
public class RepAndWarrantServiceImpl implements RepAndWarrantService{

    private final Logger log = LoggerFactory.getLogger(RepAndWarrantServiceImpl.class);
    
    @Inject
    private RepAndWarrantRepository repAndWarrantRepository;
    
    /**
     * Save a repAndWarrant.
     * 
     * @param repAndWarrant the entity to save
     * @return the persisted entity
     */
    public RepAndWarrant save(RepAndWarrant repAndWarrant) {
        log.debug("Request to save RepAndWarrant : {}", repAndWarrant);
        RepAndWarrant result = repAndWarrantRepository.save(repAndWarrant);
        return result;
    }

    /**
     *  Get all the repAndWarrants.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<RepAndWarrant> findAll() {
        log.debug("Request to get all RepAndWarrants");
        List<RepAndWarrant> result = repAndWarrantRepository.findAll();
        return result;
    }

    /**
     *  Get one repAndWarrant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RepAndWarrant findOne(Long id) {
        log.debug("Request to get RepAndWarrant : {}", id);
        RepAndWarrant repAndWarrant = repAndWarrantRepository.findOne(id);
        return repAndWarrant;
    }

    /**
     *  Delete the  repAndWarrant by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RepAndWarrant : {}", id);
        repAndWarrantRepository.delete(id);
    }
}
