package com.hrbuddy.rest.controllers;

import com.hrbuddy.services.DepartmentService;
import com.hrbuddy.services.EmployeeService;
import com.hrbuddy.services.JobService;
import com.hrbuddy.services.VacationService;
import com.hrbuddy.services.dto.DepartmentDTO;
import com.hrbuddy.services.dto.EmployeeDTO;
import com.hrbuddy.services.dto.JobDTO;
import com.hrbuddy.services.dto.VacationDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentControllerTest {

    private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImhyYnVkZHkiLCJpYXQiOjE3MTI2NTk2MTIsImV4cCI6MTcxMjc0NjAxMn0.kDb31W0cjKUwSlTXdYkrEFsXW84O7lzaWDpgm29ULFk";

    @Test
    void Get_all_departments_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/departments")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        client.close();
    }

    @Test
    void get_departments_returns_200_when_department_is_present() {

        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);

        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(BigDecimal.valueOf(34))
                .bonus(BigDecimal.valueOf(100))
                .deduction(BigDecimal.valueOf(50))
                .jobId(createdJob.getId())
                .hireDate("2024-03-08")
                .build();

        EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);

        DepartmentDTO department = DepartmentDTO.builder()
                .name("IT")
                .location("New York")
                .managerId(createdEmployee.getId())
                .build();
        DepartmentDTO createdDepartment = DepartmentService.createDepartment(department);


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/departments")
                .path("{id}")
                .resolveTemplate("id", createdDepartment.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        DepartmentService.deleteDepartment(createdDepartment.getId());
        EmployeeService.deleteEmployee(createdEmployee.getId());
        JobService.deleteJob(createdJob.getId());
        client.close();
    }

    @Test
    void get_department_returns_404_when_department_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/vacations")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(404, response.getStatus());
        client.close();
    }

    @Test
    void create_department_returns_201_on_success() {
        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);

        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(BigDecimal.valueOf(34))
                .bonus(BigDecimal.valueOf(100))
                .deduction(BigDecimal.valueOf(50))
                .jobId(createdJob.getId())
                .hireDate("2024-03-08")
                .build();

        EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);

        DepartmentDTO department = DepartmentDTO.builder()
                .name("IT")
                .location("New York")
                .managerId(createdEmployee.getId())
                .build();


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/departments")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .post(Entity.entity(department, MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());

        DepartmentService.deleteDepartment(response.readEntity(DepartmentDTO.class).getId());
        EmployeeService.deleteEmployee(createdEmployee.getId());
        JobService.deleteJob(createdJob.getId());

        client.close();
    }


    @Test
    void update_department_returns_200_on_success() {
        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);

        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(BigDecimal.valueOf(34))
                .bonus(BigDecimal.valueOf(100))
                .deduction(BigDecimal.valueOf(50))
                .jobId(createdJob.getId())
                .hireDate("2024-03-08")
                .build();

        EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);

        DepartmentDTO department = DepartmentDTO.builder()
                .name("IT")
                .location("New York")
                .managerId(createdEmployee.getId())
                .build();

        DepartmentDTO createdDepartment = DepartmentService.createDepartment(department);


        createdDepartment.setLocation("London");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/departments")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .put(Entity.entity(createdDepartment, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());


        DepartmentService.deleteDepartment(createdDepartment.getId());
        EmployeeService.deleteEmployee(createdEmployee.getId());
        JobService.deleteJob(createdJob.getId());

        client.close();

    }

    @Test
    void update_vacation_returns_400_when_job_is_not_present() {

        DepartmentDTO department = DepartmentDTO.builder()
                .id(4893043)
                .name("IT")
                .location("New York")
                .managerId(453435)
                .build();



        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/attendances")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .put(Entity.entity(department, MediaType.APPLICATION_JSON));

        assertEquals(400, response.getStatus());


        client.close();
    }

    @Test
    void delete_department_returns_200_on_success() {

        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);

        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(BigDecimal.valueOf(34))
                .bonus(BigDecimal.valueOf(100))
                .deduction(BigDecimal.valueOf(50))
                .jobId(createdJob.getId())
                .hireDate("2024-03-08")
                .build();

        EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);

        DepartmentDTO department = DepartmentDTO.builder()
                .name("IT")
                .location("New York")
                .managerId(createdEmployee.getId())
                .build();

        DepartmentDTO createdDepartment = DepartmentService.createDepartment(department);




        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/departments")
                .path("{id}")
                .resolveTemplate("id", createdDepartment.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .delete(Response.class);

        assertEquals(200, response.getStatus());

        EmployeeService.deleteEmployee(createdEmployee.getId());
        JobService.deleteJob(createdJob.getId());

        client.close();
    }
}
