package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.exceptions.BadRequestException;
import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
import com.hrbuddy.services.JobService;
import com.hrbuddy.services.dto.JobDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;


@Path("/jobs")
public class JobController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllJobs() {
        List<JobDTO> jobs = JobService.getAllJobs();
        if(jobs.isEmpty()){
            throw new ResourceNotFoundException("No jobs found");
        }
        GenericEntity<List<JobDTO>> entity = new GenericEntity<>(jobs){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getJob(@PathParam("id") int id) {
        Optional<JobDTO> job = JobService.getJob(id);
        if(job.isEmpty()){
            throw new ResourceNotFoundException("No job found for id: " + id);
        }
        return Response.ok(job.get()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createJob(JobDTO job) {
        JobDTO createdJob = JobService.createJob(job);
        return Response.status(Response.Status.CREATED).entity(createdJob).build();
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateJob(JobDTO job) {
        try {
            JobDTO updatedJob = JobService.updateJob(job);
            return Response.ok().entity(updatedJob).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response deleteJob(@PathParam("id") int id) {
        try {
            JobService.deleteJob(id);
            return Response.ok().entity("Job was deleted successfully").build();
        } catch (Exception e) {
            throw new BadRequestException("Couldn't delete the job. If the id is correct you need to delete all the employees assigned to this job first.");
        }
    }
}
