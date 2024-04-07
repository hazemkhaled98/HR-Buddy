package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Department;
import com.hrbuddy.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.Set;

public class DepartmentRepository extends Repository<Department> {


    public DepartmentRepository(EntityManager entityManager) {
        super(entityManager);
        setType(Department.class);
    }

    public Set<Employee> getEmployees(int id) {
        Optional<Department> department = get(id);
        if (department.isEmpty())
            throw new IllegalArgumentException("Department with id: " + id + " was not found");
        return department.get().getEmployees();
    }
}
