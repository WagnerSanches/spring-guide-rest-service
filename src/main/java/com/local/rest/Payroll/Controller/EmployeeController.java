package com.local.rest.Payroll.Controller;

import com.local.rest.Payroll.Entities.Assemblers.EmployeeModelAssembler;
import com.local.rest.Payroll.Entities.Employee;
import com.local.rest.Payroll.Exceptions.EmployeeNotFoundException;
import com.local.rest.Payroll.Repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = repository.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class)).withSelfRel());
    }

    @PostMapping("/employees")
    public Employee add(@RequestBody Employee employee) {
        return this.repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = this.repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    public Employee update(@RequestBody Employee updatedEmployee, @PathVariable Long id) {

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
    public void delete(@PathVariable Long id) {
        this.repository.deleteById(id);
     }

}

