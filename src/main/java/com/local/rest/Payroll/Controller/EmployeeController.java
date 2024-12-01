package com.local.rest.Payroll.Controller;

import com.local.rest.Payroll.Entities.Employee;
import com.local.rest.Payroll.Exceptions.EmployeeNotFoundException;
import com.local.rest.Payroll.Repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> all() {
        return this.repository.findAll();
    }

    @PostMapping("/employees")
    Employee add(@RequestBody Employee employee) {
        return this.repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = this.repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }

    @PutMapping("/employees/{id}")
    Employee update(@RequestBody Employee updatedEmployee, @PathVariable Long id) {

        return this.repository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    return this.repository.save(employee);
                })
                .orElseGet(() -> {
                    return this.repository.save(updatedEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void delete(@PathVariable Long id) {
        this.repository.deleteById(id);
     }

}

