package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.criteria.TransferDocumentCriteria;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
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
 * Integration tests for the {@link TransferDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferDocumentResourceIT {

    private static final TransferDocumentType DEFAULT_TYPE = TransferDocumentType.RECEIVING;
    private static final TransferDocumentType UPDATED_TYPE = TransferDocumentType.DISPATCHING;

    private static final LocalDate DEFAULT_TRANSFER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSFER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSFER_DATE = LocalDate.ofEpochDay(-1L);

    private static final TransferDocumentStatus DEFAULT_STATUS = TransferDocumentStatus.IN_PREPARATION;
    private static final TransferDocumentStatus UPDATED_STATUS = TransferDocumentStatus.ACCOUNTED;

    private static final LocalDate DEFAULT_ACCOUNTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACCOUNTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REVERSAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVERSAL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVERSAL_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/transfer-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferDocumentRepository transferDocumentRepository;

    @Autowired
    private TransferDocumentMapper transferDocumentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferDocumentMockMvc;

    private TransferDocument transferDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDocument createEntity(EntityManager em) {
        TransferDocument transferDocument = new TransferDocument()
            .type(DEFAULT_TYPE)
            .transferDate(DEFAULT_TRANSFER_DATE)
            .status(DEFAULT_STATUS)
            .accountingDate(DEFAULT_ACCOUNTING_DATE)
            .reversalDate(DEFAULT_REVERSAL_DATE);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        transferDocument.setBusinessYear(businessYear);
        return transferDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDocument createUpdatedEntity(EntityManager em) {
        TransferDocument transferDocument = new TransferDocument()
            .type(UPDATED_TYPE)
            .transferDate(UPDATED_TRANSFER_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .reversalDate(UPDATED_REVERSAL_DATE);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createUpdatedEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        transferDocument.setBusinessYear(businessYear);
        return transferDocument;
    }

    @BeforeEach
    public void initTest() {
        transferDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferDocument() throws Exception {
        int databaseSizeBeforeCreate = transferDocumentRepository.findAll().size();
        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);
        restTransferDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        TransferDocument testTransferDocument = transferDocumentList.get(transferDocumentList.size() - 1);
        assertThat(testTransferDocument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransferDocument.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testTransferDocument.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransferDocument.getAccountingDate()).isEqualTo(DEFAULT_ACCOUNTING_DATE);
        assertThat(testTransferDocument.getReversalDate()).isEqualTo(DEFAULT_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void createTransferDocumentWithExistingId() throws Exception {
        // Create the TransferDocument with an existing ID
        transferDocument.setId(1L);
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        int databaseSizeBeforeCreate = transferDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferDocumentRepository.findAll().size();
        // set the field null
        transferDocument.setType(null);

        // Create the TransferDocument, which fails.
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        restTransferDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransferDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferDocumentRepository.findAll().size();
        // set the field null
        transferDocument.setTransferDate(null);

        // Create the TransferDocument, which fails.
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        restTransferDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferDocumentRepository.findAll().size();
        // set the field null
        transferDocument.setStatus(null);

        // Create the TransferDocument, which fails.
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        restTransferDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransferDocuments() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accountingDate").value(hasItem(DEFAULT_ACCOUNTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].reversalDate").value(hasItem(DEFAULT_REVERSAL_DATE.toString())));
    }

    @Test
    @Transactional
    void getTransferDocument() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get the transferDocument
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, transferDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferDocument.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.transferDate").value(DEFAULT_TRANSFER_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.accountingDate").value(DEFAULT_ACCOUNTING_DATE.toString()))
            .andExpect(jsonPath("$.reversalDate").value(DEFAULT_REVERSAL_DATE.toString()));
    }

    @Test
    @Transactional
    void getTransferDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        Long id = transferDocument.getId();

        defaultTransferDocumentShouldBeFound("id.equals=" + id);
        defaultTransferDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultTransferDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferDocumentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where type equals to DEFAULT_TYPE
        defaultTransferDocumentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the transferDocumentList where type equals to UPDATED_TYPE
        defaultTransferDocumentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTransferDocumentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the transferDocumentList where type equals to UPDATED_TYPE
        defaultTransferDocumentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where type is not null
        defaultTransferDocumentShouldBeFound("type.specified=true");

        // Get all the transferDocumentList where type is null
        defaultTransferDocumentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate equals to DEFAULT_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.equals=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.equals=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate in DEFAULT_TRANSFER_DATE or UPDATED_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.in=" + DEFAULT_TRANSFER_DATE + "," + UPDATED_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.in=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate is not null
        defaultTransferDocumentShouldBeFound("transferDate.specified=true");

        // Get all the transferDocumentList where transferDate is null
        defaultTransferDocumentShouldNotBeFound("transferDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate is greater than or equal to DEFAULT_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.greaterThanOrEqual=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate is greater than or equal to UPDATED_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.greaterThanOrEqual=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate is less than or equal to DEFAULT_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.lessThanOrEqual=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate is less than or equal to SMALLER_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.lessThanOrEqual=" + SMALLER_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate is less than DEFAULT_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.lessThan=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate is less than UPDATED_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.lessThan=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where transferDate is greater than DEFAULT_TRANSFER_DATE
        defaultTransferDocumentShouldNotBeFound("transferDate.greaterThan=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferDocumentList where transferDate is greater than SMALLER_TRANSFER_DATE
        defaultTransferDocumentShouldBeFound("transferDate.greaterThan=" + SMALLER_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where status equals to DEFAULT_STATUS
        defaultTransferDocumentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transferDocumentList where status equals to UPDATED_STATUS
        defaultTransferDocumentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransferDocumentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transferDocumentList where status equals to UPDATED_STATUS
        defaultTransferDocumentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where status is not null
        defaultTransferDocumentShouldBeFound("status.specified=true");

        // Get all the transferDocumentList where status is null
        defaultTransferDocumentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate equals to DEFAULT_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.equals=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate equals to UPDATED_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.equals=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate in DEFAULT_ACCOUNTING_DATE or UPDATED_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.in=" + DEFAULT_ACCOUNTING_DATE + "," + UPDATED_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate equals to UPDATED_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.in=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate is not null
        defaultTransferDocumentShouldBeFound("accountingDate.specified=true");

        // Get all the transferDocumentList where accountingDate is null
        defaultTransferDocumentShouldNotBeFound("accountingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate is greater than or equal to DEFAULT_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.greaterThanOrEqual=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate is greater than or equal to UPDATED_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.greaterThanOrEqual=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate is less than or equal to DEFAULT_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.lessThanOrEqual=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate is less than or equal to SMALLER_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.lessThanOrEqual=" + SMALLER_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate is less than DEFAULT_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.lessThan=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate is less than UPDATED_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.lessThan=" + UPDATED_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByAccountingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where accountingDate is greater than DEFAULT_ACCOUNTING_DATE
        defaultTransferDocumentShouldNotBeFound("accountingDate.greaterThan=" + DEFAULT_ACCOUNTING_DATE);

        // Get all the transferDocumentList where accountingDate is greater than SMALLER_ACCOUNTING_DATE
        defaultTransferDocumentShouldBeFound("accountingDate.greaterThan=" + SMALLER_ACCOUNTING_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate equals to DEFAULT_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.equals=" + DEFAULT_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate equals to UPDATED_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.equals=" + UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate in DEFAULT_REVERSAL_DATE or UPDATED_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.in=" + DEFAULT_REVERSAL_DATE + "," + UPDATED_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate equals to UPDATED_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.in=" + UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate is not null
        defaultTransferDocumentShouldBeFound("reversalDate.specified=true");

        // Get all the transferDocumentList where reversalDate is null
        defaultTransferDocumentShouldNotBeFound("reversalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate is greater than or equal to DEFAULT_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.greaterThanOrEqual=" + DEFAULT_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate is greater than or equal to UPDATED_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.greaterThanOrEqual=" + UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate is less than or equal to DEFAULT_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.lessThanOrEqual=" + DEFAULT_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate is less than or equal to SMALLER_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.lessThanOrEqual=" + SMALLER_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate is less than DEFAULT_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.lessThan=" + DEFAULT_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate is less than UPDATED_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.lessThan=" + UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReversalDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        // Get all the transferDocumentList where reversalDate is greater than DEFAULT_REVERSAL_DATE
        defaultTransferDocumentShouldNotBeFound("reversalDate.greaterThan=" + DEFAULT_REVERSAL_DATE);

        // Get all the transferDocumentList where reversalDate is greater than SMALLER_REVERSAL_DATE
        defaultTransferDocumentShouldBeFound("reversalDate.greaterThan=" + SMALLER_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByTransferDocumentItemIsEqualToSomething() throws Exception {
        TransferDocumentItem transferDocumentItem;
        if (TestUtil.findAll(em, TransferDocumentItem.class).isEmpty()) {
            transferDocumentRepository.saveAndFlush(transferDocument);
            transferDocumentItem = TransferDocumentItemResourceIT.createEntity(em);
        } else {
            transferDocumentItem = TestUtil.findAll(em, TransferDocumentItem.class).get(0);
        }
        em.persist(transferDocumentItem);
        em.flush();
        transferDocument.addTransferDocumentItem(transferDocumentItem);
        transferDocumentRepository.saveAndFlush(transferDocument);
        Long transferDocumentItemId = transferDocumentItem.getId();

        // Get all the transferDocumentList where transferDocumentItem equals to transferDocumentItemId
        defaultTransferDocumentShouldBeFound("transferDocumentItemId.equals=" + transferDocumentItemId);

        // Get all the transferDocumentList where transferDocumentItem equals to (transferDocumentItemId + 1)
        defaultTransferDocumentShouldNotBeFound("transferDocumentItemId.equals=" + (transferDocumentItemId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByBusinessYearIsEqualToSomething() throws Exception {
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            transferDocumentRepository.saveAndFlush(transferDocument);
            businessYear = BusinessYearResourceIT.createEntity(em);
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        em.persist(businessYear);
        em.flush();
        transferDocument.setBusinessYear(businessYear);
        transferDocumentRepository.saveAndFlush(transferDocument);
        Long businessYearId = businessYear.getId();

        // Get all the transferDocumentList where businessYear equals to businessYearId
        defaultTransferDocumentShouldBeFound("businessYearId.equals=" + businessYearId);

        // Get all the transferDocumentList where businessYear equals to (businessYearId + 1)
        defaultTransferDocumentShouldNotBeFound("businessYearId.equals=" + (businessYearId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByReceivingStorageIsEqualToSomething() throws Exception {
        Storage receivingStorage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            transferDocumentRepository.saveAndFlush(transferDocument);
            receivingStorage = StorageResourceIT.createEntity(em);
        } else {
            receivingStorage = TestUtil.findAll(em, Storage.class).get(0);
        }
        em.persist(receivingStorage);
        em.flush();
        transferDocument.setReceivingStorage(receivingStorage);
        transferDocumentRepository.saveAndFlush(transferDocument);
        Long receivingStorageId = receivingStorage.getId();

        // Get all the transferDocumentList where receivingStorage equals to receivingStorageId
        defaultTransferDocumentShouldBeFound("receivingStorageId.equals=" + receivingStorageId);

        // Get all the transferDocumentList where receivingStorage equals to (receivingStorageId + 1)
        defaultTransferDocumentShouldNotBeFound("receivingStorageId.equals=" + (receivingStorageId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByDispatchingStorageIsEqualToSomething() throws Exception {
        Storage dispatchingStorage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            transferDocumentRepository.saveAndFlush(transferDocument);
            dispatchingStorage = StorageResourceIT.createEntity(em);
        } else {
            dispatchingStorage = TestUtil.findAll(em, Storage.class).get(0);
        }
        em.persist(dispatchingStorage);
        em.flush();
        transferDocument.setDispatchingStorage(dispatchingStorage);
        transferDocumentRepository.saveAndFlush(transferDocument);
        Long dispatchingStorageId = dispatchingStorage.getId();

        // Get all the transferDocumentList where dispatchingStorage equals to dispatchingStorageId
        defaultTransferDocumentShouldBeFound("dispatchingStorageId.equals=" + dispatchingStorageId);

        // Get all the transferDocumentList where dispatchingStorage equals to (dispatchingStorageId + 1)
        defaultTransferDocumentShouldNotBeFound("dispatchingStorageId.equals=" + (dispatchingStorageId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDocumentsByBusinessPartnerIsEqualToSomething() throws Exception {
        BusinessPartner businessPartner;
        if (TestUtil.findAll(em, BusinessPartner.class).isEmpty()) {
            transferDocumentRepository.saveAndFlush(transferDocument);
            businessPartner = BusinessPartnerResourceIT.createEntity(em);
        } else {
            businessPartner = TestUtil.findAll(em, BusinessPartner.class).get(0);
        }
        em.persist(businessPartner);
        em.flush();
        transferDocument.setBusinessPartner(businessPartner);
        transferDocumentRepository.saveAndFlush(transferDocument);
        Long businessPartnerId = businessPartner.getId();

        // Get all the transferDocumentList where businessPartner equals to businessPartnerId
        defaultTransferDocumentShouldBeFound("businessPartnerId.equals=" + businessPartnerId);

        // Get all the transferDocumentList where businessPartner equals to (businessPartnerId + 1)
        defaultTransferDocumentShouldNotBeFound("businessPartnerId.equals=" + (businessPartnerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferDocumentShouldBeFound(String filter) throws Exception {
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accountingDate").value(hasItem(DEFAULT_ACCOUNTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].reversalDate").value(hasItem(DEFAULT_REVERSAL_DATE.toString())));

        // Check, that the count call also returns 1
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferDocumentShouldNotBeFound(String filter) throws Exception {
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferDocument() throws Exception {
        // Get the transferDocument
        restTransferDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransferDocument() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();

        // Update the transferDocument
        TransferDocument updatedTransferDocument = transferDocumentRepository.findById(transferDocument.getId()).get();
        // Disconnect from session so that the updates on updatedTransferDocument are not directly saved in db
        em.detach(updatedTransferDocument);
        updatedTransferDocument
            .type(UPDATED_TYPE)
            .transferDate(UPDATED_TRANSFER_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .reversalDate(UPDATED_REVERSAL_DATE);
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(updatedTransferDocument);

        restTransferDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
        TransferDocument testTransferDocument = transferDocumentList.get(transferDocumentList.size() - 1);
        assertThat(testTransferDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransferDocument.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTransferDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransferDocument.getAccountingDate()).isEqualTo(UPDATED_ACCOUNTING_DATE);
        assertThat(testTransferDocument.getReversalDate()).isEqualTo(UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferDocumentWithPatch() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();

        // Update the transferDocument using partial update
        TransferDocument partialUpdatedTransferDocument = new TransferDocument();
        partialUpdatedTransferDocument.setId(transferDocument.getId());

        partialUpdatedTransferDocument.type(UPDATED_TYPE).transferDate(UPDATED_TRANSFER_DATE).status(UPDATED_STATUS);

        restTransferDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDocument))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
        TransferDocument testTransferDocument = transferDocumentList.get(transferDocumentList.size() - 1);
        assertThat(testTransferDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransferDocument.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTransferDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransferDocument.getAccountingDate()).isEqualTo(DEFAULT_ACCOUNTING_DATE);
        assertThat(testTransferDocument.getReversalDate()).isEqualTo(DEFAULT_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTransferDocumentWithPatch() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();

        // Update the transferDocument using partial update
        TransferDocument partialUpdatedTransferDocument = new TransferDocument();
        partialUpdatedTransferDocument.setId(transferDocument.getId());

        partialUpdatedTransferDocument
            .type(UPDATED_TYPE)
            .transferDate(UPDATED_TRANSFER_DATE)
            .status(UPDATED_STATUS)
            .accountingDate(UPDATED_ACCOUNTING_DATE)
            .reversalDate(UPDATED_REVERSAL_DATE);

        restTransferDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDocument))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
        TransferDocument testTransferDocument = transferDocumentList.get(transferDocumentList.size() - 1);
        assertThat(testTransferDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransferDocument.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTransferDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransferDocument.getAccountingDate()).isEqualTo(UPDATED_ACCOUNTING_DATE);
        assertThat(testTransferDocument.getReversalDate()).isEqualTo(UPDATED_REVERSAL_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferDocument() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentRepository.findAll().size();
        transferDocument.setId(count.incrementAndGet());

        // Create the TransferDocument
        TransferDocumentDTO transferDocumentDTO = transferDocumentMapper.toDto(transferDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDocument in the database
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferDocument() throws Exception {
        // Initialize the database
        transferDocumentRepository.saveAndFlush(transferDocument);

        int databaseSizeBeforeDelete = transferDocumentRepository.findAll().size();

        // Delete the transferDocument
        restTransferDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferDocument> transferDocumentList = transferDocumentRepository.findAll();
        assertThat(transferDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
