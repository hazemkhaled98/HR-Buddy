package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.messages.ErrorMessage;
import com.hrbuddy.rest.messages.ResponseMessage;
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
        GenericEntity<List<DepartmentDTO>> entity = new GenericEntity<>(departments){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDepartment(@PathParam("id") int id) {
        Optional<DepartmentDTO> department = DepartmentService.getDepartment(id);
        if(department.isEmpty()){
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
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description(ex.getMessage())
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
            throw new WebApplicationException(response);
        } catch (Exception e) {
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't create the department record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
            throw new WebApplicationException(response);
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
            ErrorMessage message = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description(ex.getMessage())
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ErrorMessage message = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't update the department record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
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
            ErrorMessage errorMessage = ErrorMessage
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't delete the department record. If the id is correct, You need to move or delete employees in this department first.")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
            throw new WebApplicationException(response);
        }
    }
}
