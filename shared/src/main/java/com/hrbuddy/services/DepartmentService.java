package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Department;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.repositories.DepartmentRepository;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.services.dto.DepartmentDTO;

import java.util.List;
import java.util.Optional;

public class DepartmentService {

    private DepartmentService() {
    }

    public static DepartmentDTO createDepartment(DepartmentDTO dto) {
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository  departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Employee> manager = employeeRepository.get(dto.getManagerId());
            if(manager.isEmpty())
                throw new IllegalArgumentException("There is not manager with id: " + dto.getManagerId());
            Department department = DepartmentDTO.toDepartment(dto);
            department.setDepartmentManager(manager.get());
            return DepartmentDTO.of(departmentRepository.create(department));
        });
    }

    public static List<DepartmentDTO> getAllDepartments() {
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository  departmentRepository = new DepartmentRepository(entityManager);
            return DepartmentDTO.of(departmentRepository.getAll());
        });
    }

    public static Optional<DepartmentDTO> getDepartment(int id) {
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            Optional<Department> department = departmentRepository.get(id);
            return department.map(DepartmentDTO::of);
        });
    }

    public static DepartmentDTO updateDepartment(DepartmentDTO dto){
        return Database.doInTransaction(entityManager -> {
            DepartmentRepository  departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Department> department = departmentRepository.get(dto.getId());
            Optional<Employee> manager = employeeRepository.get(dto.getManagerId());
            if(department.isEmpty())
                throw new IllegalArgumentException("Attendance record was not found");
            if(manager.isEmpty())
                throw new IllegalArgumentException("There is not manager with id: " + dto.getManagerId());
            Department updatedDepartment = departmentRepository.update(DepartmentDTO.toDepartment(dto));
            updatedDepartment.setDepartmentManager(manager.get());
            return DepartmentDTO.of(updatedDepartment);
        });
    }

    public static void deleteDepartment(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            departmentRepository.deleteById(id);
        });
    }

}
