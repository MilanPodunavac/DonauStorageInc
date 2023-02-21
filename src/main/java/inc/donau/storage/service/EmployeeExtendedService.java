package inc.donau.storage.service;

import inc.donau.storage.domain.User;
import inc.donau.storage.repository.EmployeeExtendedRepository;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.dto.AdminUserDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.mapper.EmployeeMapper;
import inc.donau.storage.service.mapper.UserMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeExtendedService extends EmployeeService {

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final UserService userService;
    private final UserMapper userMapper;

    private final PersonExtendedService personExtendedService;
    private final AddressExtendedService addressExtendedService;

    public EmployeeExtendedService(
        EmployeeExtendedRepository employeeRepository,
        EmployeeMapper employeeMapper,
        UserService userService,
        UserMapper userMapper,
        PersonExtendedService personExtendedService,
        AddressExtendedService addressExtendedService
    ) {
        super(employeeRepository, employeeMapper);
        this.employeeExtendedRepository = employeeRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.personExtendedService = personExtendedService;
        this.addressExtendedService = addressExtendedService;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getPersonalInfo().getId() == null) employeeDTO.setPersonalInfo(
            personExtendedService.save(employeeDTO.getPersonalInfo())
        );
        if (employeeDTO.getUser() == null || employeeDTO.getUser().getId() == null) employeeDTO.setUser(
            userMapper.userToUserDTO(
                userService.registerUser(
                    new AdminUserDTO(
                        employeeDTO.getPersonalInfo().getFirstName(),
                        employeeDTO.getPersonalInfo().getLastName(),
                        employeeDTO.getPersonalInfo().getContactInfo().getEmail()
                    ),
                    "user"
                )
            )
        );
        if (employeeDTO.getAddress().getId() == null) employeeDTO.setAddress(addressExtendedService.save(employeeDTO.getAddress()));
        return super.save(employeeDTO);
    }
}
