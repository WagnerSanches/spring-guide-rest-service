package com.local.rest.Payroll.Exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee ID " + id + " not founded!");
    }

}
