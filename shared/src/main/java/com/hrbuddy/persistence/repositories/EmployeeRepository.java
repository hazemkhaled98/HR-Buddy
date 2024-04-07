package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

public class EmployeeRepository extends Repository<Employee> {

    public EmployeeRepository(EntityManager entityManager){
        super(entityManager);
        setType(Employee.class);
    }
}
