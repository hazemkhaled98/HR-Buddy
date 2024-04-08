package com.hrbuddy.rest.controllers;

import com.hrbuddy.rest.messages.ErrorResponse;
import com.hrbuddy.rest.messages.ResponseMessage;
import com.hrbuddy.services.EmployeeService;
import com.hrbuddy.services.dto.EmployeeDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Path("/employees")
public class EmployeeController {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllEmployees(
            @QueryParam("departmentId") int departmentId,
            @QueryParam("jobId") int jobId,
            @QueryParam("managerId") int managerId,
            @QueryParam("offset") int offset,
            @QueryParam("limit") int limit,
            @QueryParam("fields") String fieldsParam
    ) {
        List<EmployeeDTO> employees = EmployeeService.getAllEmployees(departmentId, jobId, managerId, offset, limit);
        if(employees.isEmpty()){
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.NOT_FOUND.name())
                    .code(404)
                    .description("No employees found")
                    .build();
            Response response =
                    Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }

        filterEmployees(employees, fieldsParam);

        GenericEntity<List<EmployeeDTO>> entity = new GenericEntity<>(employees) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployee(@PathParam("id") int id, @QueryParam("fields") String fieldsParam) {
        Optional<EmployeeDTO> employee = EmployeeService.getEmployee(id);
        if(employee.isEmpty()){
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
        filterEmployee(employee.get(), fieldsParam);
        return Response.ok(employee.get()).build();
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createEmployee(EmployeeDTO employee) {
        try {
            EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);
            return Response.status(Response.Status.CREATED).entity(createdEmployee).build();
        } catch (IllegalArgumentException ex){
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description(ex.getMessage())
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't create the employee record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateEmployee(EmployeeDTO employee) {
        try {
            EmployeeDTO updatedEmployee = EmployeeService.updateEmployee(employee);
            return Response.ok().entity(updatedEmployee).build();
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
                    .description("Couldn't update the employee record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
        }
    }


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteEmployee(@PathParam("id") int id) {
        try {
            EmployeeService.deleteEmployee(id);
            return Response.ok().entity("Employee record was deleted successfully").build();
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't delete the Employee record. If the id is correct you need to delete all the attendance record first. Also if he is a manager you need to assign the department to another manager and update the records of the managed employees")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
    }

    private void filterEmployee(EmployeeDTO employee, String fieldsParams){
        if(fieldsParams == null || fieldsParams.isEmpty())
            return;
        List<String> fields = Arrays.asList(fieldsParams.split(","));
        List<Field> filterFields = fields.stream().map(field -> {
            try {
                return EmployeeDTO.class.getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                ErrorResponse errorResponse = ErrorResponse
                        .builder()
                        .message(ResponseMessage.BAD_REQUEST.name())
                        .code(400)
                        .description("Invalid Fields")
                        .build();
                Response response =
                        Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                throw new WebApplicationException(response);
            }
        }).toList();

        for(Field declaredField : EmployeeDTO.class.getDeclaredFields()){
            if(!filterFields.contains(declaredField)){
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(employee, null);
                } catch (IllegalAccessException e) {
                    ErrorResponse errorResponse = ErrorResponse
                            .builder()
                            .message(ResponseMessage.INTERNAL_SERVER_ERROR.name())
                            .code(500)
                            .description("The server faced issues returning the result with specified fields")
                            .build();
                    Response response =
                            Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
                    throw new WebApplicationException(response);
                }
            }
        }
    }

    private void filterEmployees(List<EmployeeDTO> employees, String fieldsParams){
        for(EmployeeDTO employee : employees){
            filterEmployee(employee, fieldsParams);
        }
    }
}
