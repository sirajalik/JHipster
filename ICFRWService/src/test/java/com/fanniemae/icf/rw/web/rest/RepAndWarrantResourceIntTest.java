package com.fanniemae.icf.rw.web.rest;

import com.fanniemae.icf.rw.FfbJavaRwApp;
import com.fanniemae.icf.rw.domain.RepAndWarrant;
import com.fanniemae.icf.rw.repository.RepAndWarrantRepository;
import com.fanniemae.icf.rw.service.RepAndWarrantService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RepAndWarrantResource REST controller.
 *
 * @see RepAndWarrantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FfbJavaRwApp.class)
@WebAppConfiguration
@IntegrationTest
public class RepAndWarrantResourceIntTest {

    private static final String DEFAULT_REP_AND_WARRANT_ID = "AAAAA";
    private static final String UPDATED_REP_AND_WARRANT_ID = "BBBBB";
    private static final String DEFAULT_SELLER_ID = "AAAAA";
    private static final String UPDATED_SELLER_ID = "BBBBB";

    @Inject
    private RepAndWarrantRepository repAndWarrantRepository;

    @Inject
    private RepAndWarrantService repAndWarrantService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRepAndWarrantMockMvc;

    private RepAndWarrant repAndWarrant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RepAndWarrantResource repAndWarrantResource = new RepAndWarrantResource();
        ReflectionTestUtils.setField(repAndWarrantResource, "repAndWarrantService", repAndWarrantService);
        this.restRepAndWarrantMockMvc = MockMvcBuilders.standaloneSetup(repAndWarrantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        repAndWarrant = new RepAndWarrant();
        repAndWarrant.setRepAndWarrantId(DEFAULT_REP_AND_WARRANT_ID);
        repAndWarrant.setSellerId(DEFAULT_SELLER_ID);
    }

    @Test
    @Transactional
    public void createRepAndWarrant() throws Exception {
        int databaseSizeBeforeCreate = repAndWarrantRepository.findAll().size();

        // Create the RepAndWarrant

        restRepAndWarrantMockMvc.perform(post("/api/rep-and-warrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repAndWarrant)))
                .andExpect(status().isCreated());

        // Validate the RepAndWarrant in the database
        List<RepAndWarrant> repAndWarrants = repAndWarrantRepository.findAll();
        assertThat(repAndWarrants).hasSize(databaseSizeBeforeCreate + 1);
        RepAndWarrant testRepAndWarrant = repAndWarrants.get(repAndWarrants.size() - 1);
        assertThat(testRepAndWarrant.getRepAndWarrantId()).isEqualTo(DEFAULT_REP_AND_WARRANT_ID);
        assertThat(testRepAndWarrant.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
    }

    @Test
    @Transactional
    public void checkRepAndWarrantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = repAndWarrantRepository.findAll().size();
        // set the field null
        repAndWarrant.setRepAndWarrantId(null);

        // Create the RepAndWarrant, which fails.

        restRepAndWarrantMockMvc.perform(post("/api/rep-and-warrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repAndWarrant)))
                .andExpect(status().isBadRequest());

        List<RepAndWarrant> repAndWarrants = repAndWarrantRepository.findAll();
        assertThat(repAndWarrants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = repAndWarrantRepository.findAll().size();
        // set the field null
        repAndWarrant.setSellerId(null);

        // Create the RepAndWarrant, which fails.

        restRepAndWarrantMockMvc.perform(post("/api/rep-and-warrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repAndWarrant)))
                .andExpect(status().isBadRequest());

        List<RepAndWarrant> repAndWarrants = repAndWarrantRepository.findAll();
        assertThat(repAndWarrants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRepAndWarrants() throws Exception {
        // Initialize the database
        repAndWarrantRepository.saveAndFlush(repAndWarrant);

        // Get all the repAndWarrants
        restRepAndWarrantMockMvc.perform(get("/api/rep-and-warrants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(repAndWarrant.getId().intValue())))
                .andExpect(jsonPath("$.[*].repAndWarrantId").value(hasItem(DEFAULT_REP_AND_WARRANT_ID.toString())))
                .andExpect(jsonPath("$.[*].sellerId").value(hasItem(DEFAULT_SELLER_ID.toString())));
    }

    @Test
    @Transactional
    public void getRepAndWarrant() throws Exception {
        // Initialize the database
        repAndWarrantRepository.saveAndFlush(repAndWarrant);

        // Get the repAndWarrant
        restRepAndWarrantMockMvc.perform(get("/api/rep-and-warrants/{id}", repAndWarrant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(repAndWarrant.getId().intValue()))
            .andExpect(jsonPath("$.repAndWarrantId").value(DEFAULT_REP_AND_WARRANT_ID.toString()))
            .andExpect(jsonPath("$.sellerId").value(DEFAULT_SELLER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRepAndWarrant() throws Exception {
        // Get the repAndWarrant
        restRepAndWarrantMockMvc.perform(get("/api/rep-and-warrants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepAndWarrant() throws Exception {
        // Initialize the database
        repAndWarrantService.save(repAndWarrant);

        int databaseSizeBeforeUpdate = repAndWarrantRepository.findAll().size();

        // Update the repAndWarrant
        RepAndWarrant updatedRepAndWarrant = new RepAndWarrant();
        updatedRepAndWarrant.setId(repAndWarrant.getId());
        updatedRepAndWarrant.setRepAndWarrantId(UPDATED_REP_AND_WARRANT_ID);
        updatedRepAndWarrant.setSellerId(UPDATED_SELLER_ID);

        restRepAndWarrantMockMvc.perform(put("/api/rep-and-warrants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRepAndWarrant)))
                .andExpect(status().isOk());

        // Validate the RepAndWarrant in the database
        List<RepAndWarrant> repAndWarrants = repAndWarrantRepository.findAll();
        assertThat(repAndWarrants).hasSize(databaseSizeBeforeUpdate);
        RepAndWarrant testRepAndWarrant = repAndWarrants.get(repAndWarrants.size() - 1);
        assertThat(testRepAndWarrant.getRepAndWarrantId()).isEqualTo(UPDATED_REP_AND_WARRANT_ID);
        assertThat(testRepAndWarrant.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
    }

    @Test
    @Transactional
    public void deleteRepAndWarrant() throws Exception {
        // Initialize the database
        repAndWarrantService.save(repAndWarrant);

        int databaseSizeBeforeDelete = repAndWarrantRepository.findAll().size();

        // Get the repAndWarrant
        restRepAndWarrantMockMvc.perform(delete("/api/rep-and-warrants/{id}", repAndWarrant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RepAndWarrant> repAndWarrants = repAndWarrantRepository.findAll();
        assertThat(repAndWarrants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
