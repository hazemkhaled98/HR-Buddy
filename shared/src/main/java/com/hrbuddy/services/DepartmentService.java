package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Department;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.repositories.DepartmentRepository;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.services.dto.DepartmentDTO;

import java.util.List;
import java.util.NoSuchElementException;

public class DepartmentService {

    private DepartmentService() {
    }

    public static DepartmentDTO createDepartment(DepartmentDTO dto) {
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository  departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Employee manager = employeeRepository.get(dto.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("There is no manager with id: " + dto.getManagerId()));
            Department department = DepartmentDTO.toDepartment(dto);
            department.setDepartmentManager(manager);
            return DepartmentDTO.of(departmentRepository.create(department));
        });
    }

    public static List<DepartmentDTO> getAllDepartments() {
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            return DepartmentDTO.of(departmentRepository.getAll());
        });
    }

    public static DepartmentDTO getDepartment(int id) {
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            Department department = departmentRepository.get(id)
                    .orElseThrow(() -> new NoSuchElementException("There is no department with id: " + id));
            return DepartmentDTO.of(department);
        });
    }

    public static DepartmentDTO updateDepartment(DepartmentDTO dto){
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository  departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            if(dto.getId() == null)
                throw new IllegalArgumentException("Department Id is required");
            Department department = departmentRepository.get(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("There is no department with id: " + dto.getId()));
            Employee manager = employeeRepository.get(dto.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("There is no manager with id: " + dto.getManagerId()));
            Department updatedDepartment = departmentRepository.update(DepartmentDTO.toDepartment(dto));
            updatedDepartment.setDepartmentManager(manager);
            return DepartmentDTO.of(updatedDepartment);
        });
    }

    public static void deleteDepartment(int id){
        try {
            Database.doInTransactionWithoutResult(entityManager -> {
                    DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
                    departmentRepository.deleteById(id);
            });
        }  catch (Exception e) {
            throw new IllegalArgumentException("Can't delete department with id: " + id + ". You need to update all the employees record associated with this department first.");
        }
    }

    private static void validateDTO(DepartmentDTO dto) {
        if(dto.getManagerId() == null || dto.getName() == null || dto.getLocation() == null)
            throw new IllegalArgumentException("Manager Id ,Department location and Department Name are required");
    }

}
