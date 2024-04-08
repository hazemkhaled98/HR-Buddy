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
import java.util.Optional;

public class EmployeeService {

    private EmployeeService() {
    }

    public static EmployeeDTO createEmployee(EmployeeDTO dto) {
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);

            Optional<Job> job = jobRepository.get(dto.getJobId());
            if(job.isEmpty())
                throw new IllegalArgumentException("There is not job with id: " + dto.getJobId());


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
            employee.setJob(job.get());
            employee.setManager(manager.orElse(null));
            return EmployeeDTO.of(employeeRepository.create(employee));
        });
    }

    public static List<EmployeeDTO> getAllEmployees() {
        return Database.doInTransaction(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            return EmployeeDTO.of(employeeRepository.getAll());
        });
    }

    public static Optional<EmployeeDTO> getEmployee(int id) {
        return Database.doInTransaction(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Employee> employee = employeeRepository.get(id);
            return employee.map(EmployeeDTO::of);
        });
    }

    public static EmployeeDTO updateEmployee(EmployeeDTO dto){
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            DepartmentRepository departmentRepository = new DepartmentRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Employee> employee = employeeRepository.get(dto.getId());
            if(employee.isEmpty())
                throw new IllegalArgumentException("There is not employee with id: " + dto.getId());

            Optional<Job> job = jobRepository.get(dto.getJobId());
            if(job.isEmpty())
                throw new IllegalArgumentException("There is not job with id: " + dto.getJobId());

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
            updatedEmployee.setJob(job.get());
            updatedEmployee.setManager(manager.orElse(null));
            updatedEmployee = employeeRepository.update(updatedEmployee);
            return EmployeeDTO.of(updatedEmployee);
        });
    }

    public static void deleteEmployee(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            employeeRepository.deleteById(id);
        });
    }
}
