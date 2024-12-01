package com.local.rest.Payroll.Repository;

import com.local.rest.Payroll.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
