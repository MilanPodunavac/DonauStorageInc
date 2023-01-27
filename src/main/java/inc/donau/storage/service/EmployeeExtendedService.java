package inc.donau.storage.service;

import inc.donau.storage.repository.EmployeeExtendedRepository;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.mapper.EmployeeMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeExtendedService extends EmployeeService {

    private final EmployeeExtendedRepository employeeExtendedRepository;

    public EmployeeExtendedService(EmployeeExtendedRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
        this.employeeExtendedRepository = employeeRepository;
    }
}
