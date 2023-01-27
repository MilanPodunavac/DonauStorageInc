package inc.donau.storage.web.rest;

import inc.donau.storage.repository.EmployeeExtendedRepository;
import inc.donau.storage.repository.EmployeeRepository;
import inc.donau.storage.service.EmployeeExtendedService;
import inc.donau.storage.service.EmployeeQueryExtendedService;
import inc.donau.storage.service.EmployeeQueryService;
import inc.donau.storage.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class EmployeeExtendedResource extends EmployeeResource {

    private final EmployeeExtendedService employeeExtendedService;
    private final EmployeeExtendedRepository employeeExtendedRepository;
    private final EmployeeQueryExtendedService employeeQueryExtendedService;

    public EmployeeExtendedResource(
        EmployeeExtendedService employeeService,
        EmployeeExtendedRepository employeeRepository,
        EmployeeQueryExtendedService employeeQueryService
    ) {
        super(employeeService, employeeRepository, employeeQueryService);
        this.employeeExtendedService = employeeService;
        this.employeeExtendedRepository = employeeRepository;
        this.employeeQueryExtendedService = employeeQueryService;
    }
}
