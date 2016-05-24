package com.fanniemae.icf.rw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fanniemae.icf.rw.domain.RepAndWarrant;
import com.fanniemae.icf.rw.service.RepAndWarrantService;
import com.fanniemae.icf.rw.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RepAndWarrant.
 */
@RestController
@RequestMapping("/api")
public class RepAndWarrantResource {

    private final Logger log = LoggerFactory.getLogger(RepAndWarrantResource.class);
        
    @Inject
    private RepAndWarrantService repAndWarrantService;
    
    /**
     * POST  /rep-and-warrants : Create a new repAndWarrant.
     *
     * @param repAndWarrant the repAndWarrant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new repAndWarrant, or with status 400 (Bad Request) if the repAndWarrant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rep-and-warrants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepAndWarrant> createRepAndWarrant(@Valid @RequestBody RepAndWarrant repAndWarrant) throws URISyntaxException {
        log.debug("REST request to save RepAndWarrant : {}", repAndWarrant);
        if (repAndWarrant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("repAndWarrant", "idexists", "A new repAndWarrant cannot already have an ID")).body(null);
        }
        RepAndWarrant result = repAndWarrantService.save(repAndWarrant);
        return ResponseEntity.created(new URI("/api/rep-and-warrants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("repAndWarrant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rep-and-warrants : Updates an existing repAndWarrant.
     *
     * @param repAndWarrant the repAndWarrant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated repAndWarrant,
     * or with status 400 (Bad Request) if the repAndWarrant is not valid,
     * or with status 500 (Internal Server Error) if the repAndWarrant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rep-and-warrants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepAndWarrant> updateRepAndWarrant(@Valid @RequestBody RepAndWarrant repAndWarrant) throws URISyntaxException {
        log.debug("REST request to update RepAndWarrant : {}", repAndWarrant);
        if (repAndWarrant.getId() == null) {
            return createRepAndWarrant(repAndWarrant);
        }
        RepAndWarrant result = repAndWarrantService.save(repAndWarrant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("repAndWarrant", repAndWarrant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rep-and-warrants : get all the repAndWarrants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of repAndWarrants in body
     */
    @RequestMapping(value = "/rep-and-warrants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RepAndWarrant> getAllRepAndWarrants() {
        log.debug("REST request to get all RepAndWarrants");
        return repAndWarrantService.findAll();
    }

    /**
     * GET  /rep-and-warrants/:id : get the "id" repAndWarrant.
     *
     * @param id the id of the repAndWarrant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the repAndWarrant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rep-and-warrants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepAndWarrant> getRepAndWarrant(@PathVariable Long id) {
        log.debug("REST request to get RepAndWarrant : {}", id);
        RepAndWarrant repAndWarrant = repAndWarrantService.findOne(id);
        return Optional.ofNullable(repAndWarrant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rep-and-warrants/:id : delete the "id" repAndWarrant.
     *
     * @param id the id of the repAndWarrant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rep-and-warrants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRepAndWarrant(@PathVariable Long id) {
        log.debug("REST request to delete RepAndWarrant : {}", id);
        repAndWarrantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("repAndWarrant", id.toString())).build();
    }

}
