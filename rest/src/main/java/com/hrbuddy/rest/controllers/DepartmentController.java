package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.DepartmentService;
import com.hrbuddy.services.dto.DepartmentDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/departments")
public class DepartmentController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllDepartments(@Context HttpHeaders headers) {

        SecurityManager.authorizeUser(headers);

        List<DepartmentDTO> departments = DepartmentService.getAllDepartments();

        GenericEntity<List<DepartmentDTO>> entity = new GenericEntity<>(departments){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDepartment(@PathParam("id") int id, @Context HttpHeaders headers) {
        SecurityManager.authorizeUser(headers);

        DepartmentDTO department = DepartmentService.getDepartment(id);

        return Response.ok(department).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createDepartment(DepartmentDTO department, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        DepartmentDTO  createdDepartment = DepartmentService.createDepartment(department);

        return Response.status(Response.Status.CREATED).entity(createdDepartment).build();
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateDepartment(DepartmentDTO department, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        DepartmentDTO updatedDepartment = DepartmentService.updateDepartment(department);

        return Response.ok().entity(updatedDepartment).build();
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response deleteDepartment(@PathParam("id") int id, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        DepartmentService.deleteDepartment(id);

        return Response.ok().entity("Department record was deleted successfully").build();
    }
}
