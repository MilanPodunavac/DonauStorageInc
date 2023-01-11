package inc.donau.storage.web.rest;

import static inc.donau.storage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.Employee;
import inc.donau.storage.domain.Person;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.mapper.EmployeeMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_UNIQUE_IDENTIFICATION_NUMBER = "5604389882208";
    private static final String UPDATED_UNIQUE_IDENTIFICATION_NUMBER = "0002341143534";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_DISABILITY = false;
    private static final Boolean UPDATED_DISABILITY = true;

    private static final Boolean DEFAULT_EMPLOYMENT = false;
    private static final Boolean UPDATED_EMPLOYMENT = true;

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .uniqueIdentificationNumber(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .disability(DEFAULT_DISABILITY)
            .employment(DEFAULT_EMPLOYMENT);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        employee.setAddress(address);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        employee.setPersonalInfo(person);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        employee.setCompany(company);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .uniqueIdentificationNumber(UPDATED_UNIQUE_IDENTIFICATION_NUMBER)
            .birthDate(UPDATED_BIRTH_DATE)
            .disability(UPDATED_DISABILITY)
            .employment(UPDATED_EMPLOYMENT);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createUpdatedEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        employee.setAddress(address);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        employee.setPersonalInfo(person);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        employee.setCompany(company);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getUniqueIdentificationNumber()).isEqualTo(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getDisability()).isEqualTo(DEFAULT_DISABILITY);
        assertThat(testEmployee.getEmployment()).isEqualTo(DEFAULT_EMPLOYMENT);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUniqueIdentificationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setUniqueIdentificationNumber(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setBirthDate(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDisability(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueIdentificationNumber").value(hasItem(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].employment").value(hasItem(DEFAULT_EMPLOYMENT.booleanValue())));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.uniqueIdentificationNumber").value(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.disability").value(DEFAULT_DISABILITY.booleanValue()))
            .andExpect(jsonPath("$.employment").value(DEFAULT_EMPLOYMENT.booleanValue()));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByUniqueIdentificationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uniqueIdentificationNumber equals to DEFAULT_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldBeFound("uniqueIdentificationNumber.equals=" + DEFAULT_UNIQUE_IDENTIFICATION_NUMBER);

        // Get all the employeeList where uniqueIdentificationNumber equals to UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldNotBeFound("uniqueIdentificationNumber.equals=" + UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByUniqueIdentificationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uniqueIdentificationNumber in DEFAULT_UNIQUE_IDENTIFICATION_NUMBER or UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldBeFound(
            "uniqueIdentificationNumber.in=" + DEFAULT_UNIQUE_IDENTIFICATION_NUMBER + "," + UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        );

        // Get all the employeeList where uniqueIdentificationNumber equals to UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldNotBeFound("uniqueIdentificationNumber.in=" + UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByUniqueIdentificationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uniqueIdentificationNumber is not null
        defaultEmployeeShouldBeFound("uniqueIdentificationNumber.specified=true");

        // Get all the employeeList where uniqueIdentificationNumber is null
        defaultEmployeeShouldNotBeFound("uniqueIdentificationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUniqueIdentificationNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uniqueIdentificationNumber contains DEFAULT_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldBeFound("uniqueIdentificationNumber.contains=" + DEFAULT_UNIQUE_IDENTIFICATION_NUMBER);

        // Get all the employeeList where uniqueIdentificationNumber contains UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldNotBeFound("uniqueIdentificationNumber.contains=" + UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByUniqueIdentificationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uniqueIdentificationNumber does not contain DEFAULT_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldNotBeFound("uniqueIdentificationNumber.doesNotContain=" + DEFAULT_UNIQUE_IDENTIFICATION_NUMBER);

        // Get all the employeeList where uniqueIdentificationNumber does not contain UPDATED_UNIQUE_IDENTIFICATION_NUMBER
        defaultEmployeeShouldBeFound("uniqueIdentificationNumber.doesNotContain=" + UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is not null
        defaultEmployeeShouldBeFound("birthDate.specified=true");

        // Get all the employeeList where birthDate is null
        defaultEmployeeShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is less than UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDisabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where disability equals to DEFAULT_DISABILITY
        defaultEmployeeShouldBeFound("disability.equals=" + DEFAULT_DISABILITY);

        // Get all the employeeList where disability equals to UPDATED_DISABILITY
        defaultEmployeeShouldNotBeFound("disability.equals=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByDisabilityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where disability in DEFAULT_DISABILITY or UPDATED_DISABILITY
        defaultEmployeeShouldBeFound("disability.in=" + DEFAULT_DISABILITY + "," + UPDATED_DISABILITY);

        // Get all the employeeList where disability equals to UPDATED_DISABILITY
        defaultEmployeeShouldNotBeFound("disability.in=" + UPDATED_DISABILITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByDisabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where disability is not null
        defaultEmployeeShouldBeFound("disability.specified=true");

        // Get all the employeeList where disability is null
        defaultEmployeeShouldNotBeFound("disability.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employment equals to DEFAULT_EMPLOYMENT
        defaultEmployeeShouldBeFound("employment.equals=" + DEFAULT_EMPLOYMENT);

        // Get all the employeeList where employment equals to UPDATED_EMPLOYMENT
        defaultEmployeeShouldNotBeFound("employment.equals=" + UPDATED_EMPLOYMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employment in DEFAULT_EMPLOYMENT or UPDATED_EMPLOYMENT
        defaultEmployeeShouldBeFound("employment.in=" + DEFAULT_EMPLOYMENT + "," + UPDATED_EMPLOYMENT);

        // Get all the employeeList where employment equals to UPDATED_EMPLOYMENT
        defaultEmployeeShouldNotBeFound("employment.in=" + UPDATED_EMPLOYMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmploymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employment is not null
        defaultEmployeeShouldBeFound("employment.specified=true");

        // Get all the employeeList where employment is null
        defaultEmployeeShouldNotBeFound("employment.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAddressIsEqualToSomething() throws Exception {
        // Get already existing entity
        Address address = employee.getAddress();
        employeeRepository.saveAndFlush(employee);
        Long addressId = address.getId();

        // Get all the employeeList where address equals to addressId
        defaultEmployeeShouldBeFound("addressId.equals=" + addressId);

        // Get all the employeeList where address equals to (addressId + 1)
        defaultEmployeeShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPersonalInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Person personalInfo = employee.getPersonalInfo();
        employeeRepository.saveAndFlush(employee);
        Long personalInfoId = personalInfo.getId();

        // Get all the employeeList where personalInfo equals to personalInfoId
        defaultEmployeeShouldBeFound("personalInfoId.equals=" + personalInfoId);

        // Get all the employeeList where personalInfo equals to (personalInfoId + 1)
        defaultEmployeeShouldNotBeFound("personalInfoId.equals=" + (personalInfoId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        employee.setCompany(company);
        employeeRepository.saveAndFlush(employee);
        Long companyId = company.getId();

        // Get all the employeeList where company equals to companyId
        defaultEmployeeShouldBeFound("companyId.equals=" + companyId);

        // Get all the employeeList where company equals to (companyId + 1)
        defaultEmployeeShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueIdentificationNumber").value(hasItem(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].employment").value(hasItem(DEFAULT_EMPLOYMENT.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .uniqueIdentificationNumber(UPDATED_UNIQUE_IDENTIFICATION_NUMBER)
            .birthDate(UPDATED_BIRTH_DATE)
            .disability(UPDATED_DISABILITY)
            .employment(UPDATED_EMPLOYMENT);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getUniqueIdentificationNumber()).isEqualTo(UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getDisability()).isEqualTo(UPDATED_DISABILITY);
        assertThat(testEmployee.getEmployment()).isEqualTo(UPDATED_EMPLOYMENT);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee.birthDate(UPDATED_BIRTH_DATE).disability(UPDATED_DISABILITY).employment(UPDATED_EMPLOYMENT);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getUniqueIdentificationNumber()).isEqualTo(DEFAULT_UNIQUE_IDENTIFICATION_NUMBER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getDisability()).isEqualTo(UPDATED_DISABILITY);
        assertThat(testEmployee.getEmployment()).isEqualTo(UPDATED_EMPLOYMENT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .uniqueIdentificationNumber(UPDATED_UNIQUE_IDENTIFICATION_NUMBER)
            .birthDate(UPDATED_BIRTH_DATE)
            .disability(UPDATED_DISABILITY)
            .employment(UPDATED_EMPLOYMENT);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getUniqueIdentificationNumber()).isEqualTo(UPDATED_UNIQUE_IDENTIFICATION_NUMBER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getDisability()).isEqualTo(UPDATED_DISABILITY);
        assertThat(testEmployee.getEmployment()).isEqualTo(UPDATED_EMPLOYMENT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
