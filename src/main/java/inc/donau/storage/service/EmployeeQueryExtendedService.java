package inc.donau.storage.service;

import inc.donau.storage.repository.EmployeeExtendedRepository;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.mapper.EmployeeMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeQueryExtendedService extends EmployeeQueryService {

    private final EmployeeExtendedRepository employeeExtendedRepository;

    public EmployeeQueryExtendedService(EmployeeExtendedRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
        this.employeeExtendedRepository = employeeRepository;
    }
}
