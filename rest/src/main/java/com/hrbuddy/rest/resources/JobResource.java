package com.hrbuddy.rest.resources;


import com.hrbuddy.rest.messages.ErrorMessage;
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
public class JobResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobs() {
        List<JobDTO> jobs = JobService.getAllJobs();
        GenericEntity<List<JobDTO>> entity = new GenericEntity<>(jobs){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJob(@PathParam("id") int id) {
        Optional<JobDTO> job = JobService.getJob(id);
        if(job.isEmpty()){
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.NOT_FOUND.name())
                    .code(404)
                    .description("Wrong ID")
                    .build();
            Response response =
                    Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
            throw new WebApplicationException(response);
        }
        return Response.ok(job.get()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(JobDTO job) {
        try {
            JobDTO createdJob = JobService.createJob(job);
            return Response.status(Response.Status.CREATED).entity(createdJob).build();
        } catch (Exception e) {
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't create the job. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
            throw new WebApplicationException(response);
        }

    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJob(JobDTO job) {
        try {
            JobDTO updatedJob = JobService.updateJob(job);
            return Response.ok().entity(updatedJob).build();
        } catch (Exception e) {
            ErrorMessage message = ErrorMessage
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
    @Path("{id}")
    public Response deleteJob(@PathParam("id") int id) {
        try {
            JobService.deleteJob(id);
            return Response.ok().entity("Job was deleted successfully").build();
        } catch (Exception e) {
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't delete the job. Invalid ID.")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
            throw new WebApplicationException(response);
        }
    }
}
