package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.JobService;
import com.hrbuddy.services.dto.JobDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;


@Path("/jobs")
public class JobController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllJobs(@Context HttpHeaders headers) {

        SecurityManager.authorizeUser(headers);

        List<JobDTO> jobs = JobService.getAllJobs();

        GenericEntity<List<JobDTO>> entity = new GenericEntity<>(jobs){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getJob(@PathParam("id") int id, @Context HttpHeaders headers) {

        SecurityManager.authorizeUser(headers);

        JobDTO job = JobService.getJob(id);

        return Response.ok(job).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createJob(JobDTO job, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        JobDTO createdJob = JobService.createJob(job);

        return Response.status(Response.Status.CREATED).entity(createdJob).build();
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateJob(JobDTO job, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        JobDTO updatedJob = JobService.updateJob(job);

        return Response.ok().entity(updatedJob).build();
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response deleteJob(@PathParam("id") int id, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        JobService.deleteJob(id);

        return Response.ok().entity("Job was deleted successfully").build();
    }
}
