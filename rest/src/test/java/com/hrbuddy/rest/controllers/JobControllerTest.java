package com.hrbuddy.rest.controllers;

import com.hrbuddy.services.JobService;
import com.hrbuddy.services.dto.JobDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;



class JobControllerTest {

    private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImhyYnVkZHkiLCJpYXQiOjE3MTI2NTk2MTIsImV4cCI6MTcxMjc0NjAxMn0.kDb31W0cjKUwSlTXdYkrEFsXW84O7lzaWDpgm29ULFk";

    @Test
    void Get_all_jobs_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        client.close();
    }

    @Test
    void get_job_returns_200_when_job_is_present() {
        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .path("{id}")
                .resolveTemplate("id", createdJob.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        JobService.deleteJob(createdJob.getId());
        client.close();
    }

    @Test
    void get_job_returns_404_when_job_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get(Response.class);

        assertEquals(404, response.getStatus());
        client.close();
    }

    @Test
    void create_job_returns_201_on_success() {
        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .post(Entity.entity(job, MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());

        JobService.deleteJob(response.readEntity(JobDTO.class).getId());

        client.close();
    }


    @Test
    void update_job_returns_200_on_success() {
        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();

        JobDTO testJob = JobService.createJob(job);


        testJob.setTitle("Software Engineer II");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .put(Entity.entity(testJob, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());


        JobService.deleteJob(testJob.getId());
        client.close();

    }

    @Test
    void update_job_returns_400_when_job_is_not_present() {

        JobDTO job = JobDTO.builder()
                .id(859034)
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .put(Entity.entity(job, MediaType.APPLICATION_JSON));

        assertEquals(400, response.getStatus());


        client.close();
    }

    @Test
    void delete_job_returns_200_on_success() {


        JobDTO job = JobDTO.builder()
                .title("Software Engineer")
                .minSalary(BigDecimal.valueOf(1000))
                .maxSalary(BigDecimal.valueOf(2000))
                .build();
        JobDTO createdJob = JobService.createJob(job);



        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hrbuddy/v1/jobs")
                .path("{id}")
                .resolveTemplate("id", createdJob.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .delete(Response.class);


        assertEquals(200, response.getStatus());

        client.close();
    }

}