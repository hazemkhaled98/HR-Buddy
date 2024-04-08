package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.messages.ErrorResponse;
import com.hrbuddy.rest.messages.ResponseMessage;
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
        GenericEntity<List<JobDTO>> entity = new GenericEntity<>(jobs){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getJob(@PathParam("id") int id) {
        Optional<JobDTO> job = JobService.getJob(id);
        if(job.isEmpty()){
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.NOT_FOUND.name())
                    .code(404)
                    .description("Wrong ID")
                    .build();
            Response response =
                    Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
        return Response.ok(job.get()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createJob(JobDTO job) {
        try {
            JobDTO createdJob = JobService.createJob(job);
            return Response.status(Response.Status.CREATED).entity(createdJob).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't create the job. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateJob(JobDTO job) {
        try {
            JobDTO updatedJob = JobService.updateJob(job);
            return Response.ok().entity(updatedJob).build();
        } catch (IllegalArgumentException ex){
            ErrorResponse message = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description(ex.getMessage())
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
        } catch (Exception e) {
            ErrorResponse message = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't update the job. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
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
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't delete the Job record. If the id is correct, You need to delete employees with this job id first.")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
    }
}
