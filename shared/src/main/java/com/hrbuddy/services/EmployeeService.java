package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Department;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.entities.Job;
import com.hrbuddy.persistence.repositories.DepartmentRepository;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.persistence.repositories.JobRepository;
import com.hrbuddy.services.dto.EmployeeDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EmployeeService {

    private EmployeeService() {
    }

    public static EmployeeDTO createEmployee(EmployeeDTO dto) {
        validateDTO(dto);
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);

            Job job = jobRepository.get(dto.getJobId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not job with id: " + dto.getJobId()));


            Optional<Department> department = Optional.empty();
            if(dto.getDepartmentId() != null){
                department = departmentRepository.get(dto.getDepartmentId());
                if(department.isEmpty())
                    throw new IllegalArgumentException("There is not department with id: " + dto.getDepartmentId());
            }

            Optional<Employee> manager = Optional.empty();
            if(dto.getManagerId() != null){
                manager = employeeRepository.get(dto.getManagerId());
                if(manager.isEmpty())
                    throw new IllegalArgumentException("There is not manager with id: " + dto.getManagerId());
            }


            Employee employee = EmployeeDTO.toEmployee(dto);
            employee.setDepartment(department.orElse(null));
            employee.setJob(job);
            employee.setManager(manager.orElse(null));
            return EmployeeDTO.of(employeeRepository.create(employee));
        });
    }

    public static List<EmployeeDTO> getAllEmployees(int departmentId, int jobId, int managerId, int offset, int limit) {
        return Database.doInTransaction(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            return EmployeeDTO.of(employeeRepository.getAll(departmentId, jobId, managerId, offset, limit));
        });
    }

    public static List<EmployeeDTO> getAllEmployees() {
        return Database.doInTransaction(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            return EmployeeDTO.of(employeeRepository.getAll());
        });
    }

    public static EmployeeDTO getEmployee(int id) {
        return Database.doInTransaction(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
           Employee employee = employeeRepository.get(id)
                   .orElseThrow(() -> new NoSuchElementException("There is not employee with id: " + id));
            return EmployeeDTO.of(employee);
        });
    }


    public static EmployeeDTO updateEmployee(EmployeeDTO dto){
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            if(dto.getId() == null)
                throw new IllegalArgumentException("Employee Id is required");
            Employee employee = employeeRepository.get(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not employee with id: " + dto.getId()));

            Job job = jobRepository.get(dto.getJobId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not job with id: " + dto.getJobId()));

            Optional<Department> department = Optional.empty();
            if(dto.getDepartmentId() != null){
                department = departmentRepository.get(dto.getDepartmentId());
                if(department.isEmpty())
                    throw new IllegalArgumentException("There is not department with id: " + dto.getDepartmentId());
            }

            Optional<Employee> manager = Optional.empty();
            if(dto.getManagerId() != null){
                manager = employeeRepository.get(dto.getManagerId());
                if(manager.isEmpty())
                    throw new IllegalArgumentException("There is not manager with id: " + dto.getManagerId());
            }


            Employee updatedEmployee = EmployeeDTO.toEmployee(dto);
            updatedEmployee.setDepartment(department.orElse(null));
            updatedEmployee.setJob(job);
            updatedEmployee.setManager(manager.orElse(null));
            updatedEmployee = employeeRepository.update(updatedEmployee);
            return EmployeeDTO.of(updatedEmployee);
        });
    }

    public static void deleteEmployee(int id){
        try {
            Database.doInTransactionWithoutResult(entityManager -> {
                    EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
                    employeeRepository.deleteById(id);
            });
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Can't delete employee with id: " + id + ". You need to delete all the attendance and vacations first. Also if this employee is a manager, You need to update the managed department and employees records.");
        }
    }

    private static void validateDTO(EmployeeDTO dto) {
        if (dto.getFirstName() == null || dto.getLastName() == null || dto.getEmail() == null
                || dto.getBonus() == null || dto.getDeduction() == null
                || dto.getSalary() == null || dto.getHireDate() == null
                || dto.getJobId() == null || dto.getPhoneNumber() == null) {
            throw new IllegalArgumentException("First name, last name, email, bonus, deduction, salary, hire date, job id and phone number are required.");
        }
    }
}
