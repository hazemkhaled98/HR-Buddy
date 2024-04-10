package com.hrbuddy.rest.controllers;

import com.hrbuddy.services.AttendanceService;
import com.hrbuddy.services.EmployeeService;
import com.hrbuddy.services.JobService;
import com.hrbuddy.services.dto.AttendanceDTO;
import com.hrbuddy.services.dto.EmployeeDTO;
import com.hrbuddy.services.dto.JobDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceControllerTest {


        private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImhyYnVkZHkiLCJpYXQiOjE3MTI2NTk2MTIsImV4cCI6MTcxMjc0NjAxMn0.kDb31W0cjKUwSlTXdYkrEFsXW84O7lzaWDpgm29ULFk";

        @Test
        void Get_all_attendance_returns_200_Ok() {
            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .get(Response.class);

            assertEquals(200, response.getStatus());

            client.close();
        }

        @Test
        void get_attendance_returns_200_when_attendance_is_present() {

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

            AttendanceDTO attendance = AttendanceDTO.builder()
                    .date("2022-01-01")
                    .status("Present")
                    .employeeId(createdEmployee.getId())
                    .build();
            AttendanceDTO createdAttendance = AttendanceService.createAttendance(attendance);

            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .path("{id}")
                    .resolveTemplate("id", createdAttendance.getId())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .get(Response.class);

            assertEquals(200, response.getStatus());

            AttendanceService.deleteAttendance(createdAttendance.getId());
            EmployeeService.deleteEmployee(createdEmployee.getId());
            JobService.deleteJob(createdJob.getId());
            client.close();
        }

        @Test
        void get_attendance_returns_404_when_attendance_is_not_present() {

            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .path("{id}")
                    .resolveTemplate("id", 145674465)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .get(Response.class);

            assertEquals(404, response.getStatus());
            client.close();
        }

        @Test
        void create_attendance_returns_201_on_success() {
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

            AttendanceDTO attendance = AttendanceDTO.builder()
                    .date("2022-01-01")
                    .status("Present")
                    .employeeId(createdEmployee.getId())
                    .build();


            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .post(Entity.entity(attendance, MediaType.APPLICATION_JSON));

            assertEquals(201, response.getStatus());

            AttendanceService.deleteAttendance(response.readEntity(AttendanceDTO.class).getId());
            EmployeeService.deleteEmployee(createdEmployee.getId());
            JobService.deleteJob(createdJob.getId());

            client.close();
        }


        @Test
        void update_attendance_returns_200_on_success() {
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

            AttendanceDTO attendance = AttendanceDTO.builder()
                    .date("2022-01-01")
                    .status("Present")
                    .employeeId(createdEmployee.getId())
                    .build();

            AttendanceDTO createdAttendance = AttendanceService.createAttendance(attendance);


            createdAttendance.setStatus("Late");

            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .put(Entity.entity(createdAttendance, MediaType.APPLICATION_JSON));

            assertEquals(200, response.getStatus());


            AttendanceService.deleteAttendance(createdAttendance.getId());
            EmployeeService.deleteEmployee(createdEmployee.getId());
            JobService.deleteJob(createdJob.getId());

            client.close();

        }

        @Test
        void update_attendance_returns_400_when_attendance_is_not_present() {

            AttendanceDTO attendance = AttendanceDTO.builder()
                    .id(45644)
                    .date("2022-01-01")
                    .status("Present")
                    .employeeId(859034)
                    .build();



            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .put(Entity.entity(attendance, MediaType.APPLICATION_JSON));

            assertEquals(400, response.getStatus());


            client.close();
        }

        @Test
        void delete_attendance_returns_200_on_success() {

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

            AttendanceDTO attendance = AttendanceDTO.builder()
                    .date("2022-01-01")
                    .status("Present")
                    .employeeId(createdEmployee.getId())
                    .build();
            AttendanceDTO createdAttendance = AttendanceService.createAttendance(attendance);





            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hrbuddy/v1/attendances")
                    .path("{id}")
                    .resolveTemplate("id", createdAttendance.getId())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + TOKEN)
                    .delete(Response.class);


            assertEquals(200, response.getStatus());

            EmployeeService.deleteEmployee(createdEmployee.getId());
            JobService.deleteJob(createdJob.getId());

            client.close();
        }

}

