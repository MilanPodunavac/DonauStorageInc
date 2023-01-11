package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.domain.Employee;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.criteria.CensusDocumentCriteria;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CensusDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CensusDocumentResourceIT {

    private static final LocalDate DEFAULT_CENSUS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CENSUS_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CENSUS_DATE = LocalDate.ofEpochDay(-1L);

    private static final CensusDocumentStatus DEFAULT_STATUS = CensusDocumentStatus.INCOMPLETE;
    private static final CensusDocumentStatus UPDATED_STATUS = CensusDocumentStatus.ACCOUNTED;

    private static final LocalDate DEFAULT_ACCOUNTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACCOUNTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_LEVELING = false;
    private static final Boolean UPDATED_LEVELING = true;

    private static final String ENTITY_API_URL = "/api/census-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CensusDocumentRepository censusDocumentRepository;

    @Autowired
    private CensusDocumentMapper censusDocumentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCensusDocumentMockMvc;

    private CensusDocument censusDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CensusDocument createEntity(EntityManager em) {
        CensusDocument censusDocument = new CensusDocument()
            .censusDate(DEFAULT_CENSUS_DATE)
            .status(DEFAULT_STATUS)
            .accountingDate(DEFAULT_ACCOUNTING_DATE)
            .leveling(DEFAULT_LEVELING);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        censusDocument.setBusinessYear(businessYear);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        censusDocument.setPresident(employee);
        // Add required entity
        censusDocument.setDeputy(employee);
        // Add required entity
        censusDocument.setCensusTaker(employee);
        // Add required entity
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            storage = StorageResourceIT.createEntity(em);
            em.persist(storage);
            em.flush();
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        censusDocument.setStorage(storage);
        return censusDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CensusDocument createUpdatedEntity(EntityManager em) {
        CensusDocument censusDocument = new CensusDocument()
            .censusDate(UPDATED_CENSUS_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .leveling(UPDATED_LEVELING);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createUpdatedEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        censusDocument.setBusinessYear(businessYear);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        censusDocument.setPresident(employee);
        // Add required entity
        censusDocument.setDeputy(employee);
        // Add required entity
        censusDocument.setCensusTaker(employee);
        // Add required entity
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            storage = StorageResourceIT.createUpdatedEntity(em);
            em.persist(storage);
            em.flush();
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        censusDocument.setStorage(storage);
        return censusDocument;
    }

    @BeforeEach
    public void initTest() {
        censusDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createCensusDocument() throws Exception {
        int databaseSizeBeforeCreate = censusDocumentRepository.findAll().size();
        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);
        restCensusDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        CensusDocument testCensusDocument = censusDocumentList.get(censusDocumentList.size() - 1);
        assertThat(testCensusDocument.getCensusDate()).isEqualTo(DEFAULT_CENSUS_DATE);
        assertThat(testCensusDocument.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCensusDocument.getAccountingDate()).isEqualTo(DEFAULT_ACCOUNTING_DATE);
        assertThat(testCensusDocument.getLeveling()).isEqualTo(DEFAULT_LEVELING);
    }

    @Test
    @Transactional
    void createCensusDocumentWithExistingId() throws Exception {
        // Create the CensusDocument with an existing ID
        censusDocument.setId(1L);
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        int databaseSizeBeforeCreate = censusDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCensusDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCensusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = censusDocumentRepository.findAll().size();
        // set the field null
        censusDocument.setCensusDate(null);

        // Create the CensusDocument, which fails.
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        restCensusDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCensusDocuments() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(censusDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].censusDate").value(hasItem(DEFAULT_CENSUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accountingDate").value(hasItem(DEFAULT_ACCOUNTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].leveling").value(hasItem(DEFAULT_LEVELING.booleanValue())));
    }

    @Test
    @Transactional
    void getCensusDocument() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get the censusDocument
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, censusDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(censusDocument.getId().intValue()))
            .andExpect(jsonPath("$.censusDate").value(DEFAULT_CENSUS_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.accountingDate").value(DEFAULT_ACCOUNTING_DATE.toString()))
            .andExpect(jsonPath("$.leveling").value(DEFAULT_LEVELING.booleanValue()));
    }

    @Test
    @Transactional
    void getCensusDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        Long id = censusDocument.getId();

        defaultCensusDocumentShouldBeFound("id.equals=" + id);
        defaultCensusDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultCensusDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCensusDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultCensusDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCensusDocumentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate equals to DEFAULT_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.equals=" + DEFAULT_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate equals to UPDATED_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.equals=" + UPDATED_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsInShouldWork() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate in DEFAULT_CENSUS_DATE or UPDATED_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.in=" + DEFAULT_CENSUS_DATE + "," + UPDATED_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate equals to UPDATED_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.in=" + UPDATED_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate is not null
        defaultCensusDocumentShouldBeFound("censusDate.specified=true");

        // Get all the censusDocumentList where censusDate is null
        defaultCensusDocumentShouldNotBeFound("censusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate is greater than or equal to DEFAULT_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.greaterThanOrEqual=" + DEFAULT_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate is greater than or equal to UPDATED_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.greaterThanOrEqual=" + UPDATED_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate is less than or equal to DEFAULT_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.lessThanOrEqual=" + DEFAULT_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate is less than or equal to SMALLER_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.lessThanOrEqual=" + SMALLER_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsLessThanSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate is less than DEFAULT_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.lessThan=" + DEFAULT_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate is less than UPDATED_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.lessThan=" + UPDATED_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where censusDate is greater than DEFAULT_CENSUS_DATE
        defaultCensusDocumentShouldNotBeFound("censusDate.greaterThan=" + DEFAULT_CENSUS_DATE);

        // Get all the censusDocumentList where censusDate is greater than SMALLER_CENSUS_DATE
        defaultCensusDocumentShouldBeFound("censusDate.greaterThan=" + SMALLER_CENSUS_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where status equals to DEFAULT_STATUS
        defaultCensusDocumentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the censusDocumentList where status equals to UPDATED_STATUS
        defaultCensusDocumentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCensusDocumentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the censusDocumentList where status equals to UPDATED_STATUS
        defaultCensusDocumentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where status is not null
        defaultCensusDocumentShouldBeFound("status.specified=true");

        // Get all the censusDocumentList where status is null
        defaultCensusDocumentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate equals to DEFAULT_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.equals=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate equals to UPDATED_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.equals=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsInShouldWork() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate in DEFAULT_ACCOUNTING_DATE or UPDATED_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.in=" + DEFAULT_ACCOUNTING_DATE + "," + UPDATED_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate equals to UPDATED_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.in=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate is not null
        defaultCensusDocumentShouldBeFound("accountingDate.specified=true");

        // Get all the censusDocumentList where accountingDate is null
        defaultCensusDocumentShouldNotBeFound("accountingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate is greater than or equal to DEFAULT_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.greaterThanOrEqual=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate is greater than or equal to UPDATED_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.greaterThanOrEqual=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate is less than or equal to DEFAULT_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.lessThanOrEqual=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate is less than or equal to SMALLER_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.lessThanOrEqual=" + SMALLER_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate is less than DEFAULT_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.lessThan=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate is less than UPDATED_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.lessThan=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByAccountingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where accountingDate is greater than DEFAULT_ACCOUNTING_DATE
        defaultCensusDocumentShouldNotBeFound("accountingDate.greaterThan=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the censusDocumentList where accountingDate is greater than SMALLER_ACCOUNTING_DATE
        defaultCensusDocumentShouldBeFound("accountingDate.greaterThan=" + SMALLER_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByLevelingIsEqualToSomething() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where leveling equals to DEFAULT_LEVELING
        defaultCensusDocumentShouldBeFound("leveling.equals=" + DEFAULT_LEVELING);

        // Get all the censusDocumentList where leveling equals to UPDATED_LEVELING
        defaultCensusDocumentShouldNotBeFound("leveling.equals=" + UPDATED_LEVELING);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByLevelingIsInShouldWork() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where leveling in DEFAULT_LEVELING or UPDATED_LEVELING
        defaultCensusDocumentShouldBeFound("leveling.in=" + DEFAULT_LEVELING + "," + UPDATED_LEVELING);

        // Get all the censusDocumentList where leveling equals to UPDATED_LEVELING
        defaultCensusDocumentShouldNotBeFound("leveling.in=" + UPDATED_LEVELING);
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByLevelingIsNullOrNotNull() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        // Get all the censusDocumentList where leveling is not null
        defaultCensusDocumentShouldBeFound("leveling.specified=true");

        // Get all the censusDocumentList where leveling is null
        defaultCensusDocumentShouldNotBeFound("leveling.specified=false");
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusItemIsEqualToSomething() throws Exception {
        CensusItem censusItem;
        if (TestUtil.findAll(em, CensusItem.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            censusItem = CensusItemResourceIT.createEntity(em);
        } else {
            censusItem = TestUtil.findAll(em, CensusItem.class).get(0);
        }
        em.persist(censusItem);
        em.flush();
        censusDocument.addCensusItem(censusItem);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long censusItemId = censusItem.getId();

        // Get all the censusDocumentList where censusItem equals to censusItemId
        defaultCensusDocumentShouldBeFound("censusItemId.equals=" + censusItemId);

        // Get all the censusDocumentList where censusItem equals to (censusItemId + 1)
        defaultCensusDocumentShouldNotBeFound("censusItemId.equals=" + (censusItemId + 1));
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByBusinessYearIsEqualToSomething() throws Exception {
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            businessYear = BusinessYearResourceIT.createEntity(em);
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        em.persist(businessYear);
        em.flush();
        censusDocument.setBusinessYear(businessYear);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long businessYearId = businessYear.getId();

        // Get all the censusDocumentList where businessYear equals to businessYearId
        defaultCensusDocumentShouldBeFound("businessYearId.equals=" + businessYearId);

        // Get all the censusDocumentList where businessYear equals to (businessYearId + 1)
        defaultCensusDocumentShouldNotBeFound("businessYearId.equals=" + (businessYearId + 1));
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByPresidentIsEqualToSomething() throws Exception {
        Employee president;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            president = EmployeeResourceIT.createEntity(em);
        } else {
            president = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(president);
        em.flush();
        censusDocument.setPresident(president);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long presidentId = president.getId();

        // Get all the censusDocumentList where president equals to presidentId
        defaultCensusDocumentShouldBeFound("presidentId.equals=" + presidentId);

        // Get all the censusDocumentList where president equals to (presidentId + 1)
        defaultCensusDocumentShouldNotBeFound("presidentId.equals=" + (presidentId + 1));
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByDeputyIsEqualToSomething() throws Exception {
        Employee deputy;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            deputy = EmployeeResourceIT.createEntity(em);
        } else {
            deputy = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(deputy);
        em.flush();
        censusDocument.setDeputy(deputy);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long deputyId = deputy.getId();

        // Get all the censusDocumentList where deputy equals to deputyId
        defaultCensusDocumentShouldBeFound("deputyId.equals=" + deputyId);

        // Get all the censusDocumentList where deputy equals to (deputyId + 1)
        defaultCensusDocumentShouldNotBeFound("deputyId.equals=" + (deputyId + 1));
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByCensusTakerIsEqualToSomething() throws Exception {
        Employee censusTaker;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            censusTaker = EmployeeResourceIT.createEntity(em);
        } else {
            censusTaker = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(censusTaker);
        em.flush();
        censusDocument.setCensusTaker(censusTaker);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long censusTakerId = censusTaker.getId();

        // Get all the censusDocumentList where censusTaker equals to censusTakerId
        defaultCensusDocumentShouldBeFound("censusTakerId.equals=" + censusTakerId);

        // Get all the censusDocumentList where censusTaker equals to (censusTakerId + 1)
        defaultCensusDocumentShouldNotBeFound("censusTakerId.equals=" + (censusTakerId + 1));
    }

    @Test
    @Transactional
    void getAllCensusDocumentsByStorageIsEqualToSomething() throws Exception {
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            censusDocumentRepository.saveAndFlush(censusDocument);
            storage = StorageResourceIT.createEntity(em);
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        em.persist(storage);
        em.flush();
        censusDocument.setStorage(storage);
        censusDocumentRepository.saveAndFlush(censusDocument);
        Long storageId = storage.getId();

        // Get all the censusDocumentList where storage equals to storageId
        defaultCensusDocumentShouldBeFound("storageId.equals=" + storageId);

        // Get all the censusDocumentList where storage equals to (storageId + 1)
        defaultCensusDocumentShouldNotBeFound("storageId.equals=" + (storageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCensusDocumentShouldBeFound(String filter) throws Exception {
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(censusDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].censusDate").value(hasItem(DEFAULT_CENSUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accountingDate").value(hasItem(DEFAULT_ACCOUNTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].leveling").value(hasItem(DEFAULT_LEVELING.booleanValue())));

        // Check, that the count call also returns 1
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCensusDocumentShouldNotBeFound(String filter) throws Exception {
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCensusDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCensusDocument() throws Exception {
        // Get the censusDocument
        restCensusDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCensusDocument() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();

        // Update the censusDocument
        CensusDocument updatedCensusDocument = censusDocumentRepository.findById(censusDocument.getId()).get();
        // Disconnect from session so that the updates on updatedCensusDocument are not directly saved in db
        em.detach(updatedCensusDocument);
        updatedCensusDocument
            .censusDate(UPDATED_CENSUS_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .leveling(UPDATED_LEVELING);
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(updatedCensusDocument);

        restCensusDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, censusDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isOk());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
        CensusDocument testCensusDocument = censusDocumentList.get(censusDocumentList.size() - 1);
        assertThat(testCensusDocument.getCensusDate()).isEqualTo(UPDATED_CENSUS_DATE);
        assertThat(testCensusDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCensusDocument.getAccountingDate()).isEqualTo(UPDATED_ACCOUNTING_DATE);
        assertThat(testCensusDocument.getLeveling()).isEqualTo(UPDATED_LEVELING);
    }

    @Test
    @Transactional
    void putNonExistingCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, censusDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCensusDocumentWithPatch() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();

        // Update the censusDocument using partial update
        CensusDocument partialUpdatedCensusDocument = new CensusDocument();
        partialUpdatedCensusDocument.setId(censusDocument.getId());

        partialUpdatedCensusDocument.censusDate(UPDATED_CENSUS_DATE).accountingDate(UPDATED_ACCOUNTING_DATE).leveling(UPDATED_LEVELING);

        restCensusDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCensusDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCensusDocument))
            )
            .andExpect(status().isOk());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
        CensusDocument testCensusDocument = censusDocumentList.get(censusDocumentList.size() - 1);
        assertThat(testCensusDocument.getCensusDate()).isEqualTo(UPDATED_CENSUS_DATE);
        assertThat(testCensusDocument.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCensusDocument.getAccountingDate()).isEqualTo(UPDATED_ACCOUNTING_DATE);
        assertThat(testCensusDocument.getLeveling()).isEqualTo(UPDATED_LEVELING);
    }

    @Test
    @Transactional
    void fullUpdateCensusDocumentWithPatch() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();

        // Update the censusDocument using partial update
        CensusDocument partialUpdatedCensusDocument = new CensusDocument();
        partialUpdatedCensusDocument.setId(censusDocument.getId());

        partialUpdatedCensusDocument
            .censusDate(UPDATED_CENSUS_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .leveling(UPDATED_LEVELING);

        restCensusDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCensusDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCensusDocument))
            )
            .andExpect(status().isOk());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
        CensusDocument testCensusDocument = censusDocumentList.get(censusDocumentList.size() - 1);
        assertThat(testCensusDocument.getCensusDate()).isEqualTo(UPDATED_CENSUS_DATE);
        assertThat(testCensusDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCensusDocument.getAccountingDate()).isEqualTo(UPDATED_ACCOUNTING_DATE);
        assertThat(testCensusDocument.getLeveling()).isEqualTo(UPDATED_LEVELING);
    }

    @Test
    @Transactional
    void patchNonExistingCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, censusDocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCensusDocument() throws Exception {
        int databaseSizeBeforeUpdate = censusDocumentRepository.findAll().size();
        censusDocument.setId(count.incrementAndGet());

        // Create the CensusDocument
        CensusDocumentDTO censusDocumentDTO = censusDocumentMapper.toDto(censusDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(censusDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CensusDocument in the database
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCensusDocument() throws Exception {
        // Initialize the database
        censusDocumentRepository.saveAndFlush(censusDocument);

        int databaseSizeBeforeDelete = censusDocumentRepository.findAll().size();

        // Delete the censusDocument
        restCensusDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, censusDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CensusDocument> censusDocumentList = censusDocumentRepository.findAll();
        assertThat(censusDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
