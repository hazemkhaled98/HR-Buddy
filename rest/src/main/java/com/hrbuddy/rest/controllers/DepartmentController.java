package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
import com.hrbuddy.services.DepartmentService;
import com.hrbuddy.services.dto.DepartmentDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/departments")
public class DepartmentController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllDepartments() {
        List<DepartmentDTO> departments = DepartmentService.getAllDepartments();
        if(departments.isEmpty()){
            throw new ResourceNotFoundException("No departments found");
        }
        GenericEntity<List<DepartmentDTO>> entity = new GenericEntity<>(departments){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDepartment(@PathParam("id") int id) {
        Optional<DepartmentDTO> department = DepartmentService.getDepartment(id);
        if(department.isEmpty()){
            throw new ResourceNotFoundException("No department found for id: " + id);
        }
        return Response.ok(department.get()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createDepartment(DepartmentDTO department) {
        try {
            DepartmentDTO  createdDepartment = DepartmentService.createDepartment(department);
            return Response.status(Response.Status.CREATED).entity(createdDepartment).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateDepartment(DepartmentDTO department) {
        try {
            DepartmentDTO updatedDepartment = DepartmentService.updateDepartment(department);
            return Response.ok().entity(updatedDepartment).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response deleteDepartment(@PathParam("id") int id) {
        try {
            DepartmentService.deleteDepartment(id);
            return Response.ok().entity("Department record was deleted successfully").build();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Couldn't delete the department record. If the id is correct, You need to move or delete employees in this department first.");
        }
    }
}
