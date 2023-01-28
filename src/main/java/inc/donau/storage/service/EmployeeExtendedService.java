package inc.donau.storage.service;

import inc.donau.storage.repository.EmployeeExtendedRepository;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.mapper.EmployeeMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeExtendedService extends EmployeeService {

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final PersonExtendedService personExtendedService;
    private final AddressExtendedService addressExtendedService;

    public EmployeeExtendedService(
        EmployeeExtendedRepository employeeRepository,
        EmployeeMapper employeeMapper,
        PersonExtendedService personExtendedService,
        AddressExtendedService addressExtendedService
    ) {
        super(employeeRepository, employeeMapper);
        this.employeeExtendedRepository = employeeRepository;
        this.personExtendedService = personExtendedService;
        this.addressExtendedService = addressExtendedService;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getPersonalInfo().getId() == null) employeeDTO.setPersonalInfo(
            personExtendedService.save(employeeDTO.getPersonalInfo())
        );
        if (employeeDTO.getAddress().getId() == null) employeeDTO.setAddress(addressExtendedService.save(employeeDTO.getAddress()));
        return super.save(employeeDTO);
    }
}
