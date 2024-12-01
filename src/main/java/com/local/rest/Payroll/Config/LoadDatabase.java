package com.local.rest.Payroll.Config;

import com.local.rest.Payroll.Entities.Employee;
import com.local.rest.Payroll.Repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            logger.info("Preloading: " + employeeRepository.save(new Employee("Hero", "Batman")));
            logger.info("Preloading: " + employeeRepository.save(new Employee("Hero", "Superman")));
        };
    }
}
