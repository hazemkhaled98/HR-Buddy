package com.hrbuddy.rest.controllers;

import com.hrbuddy.rest.exceptions.BadRequestException;
import com.hrbuddy.rest.exceptions.InternalServerErrorException;
import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("No employees found");
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
            throw new ResourceNotFoundException("No employee found for id: " + id);
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
            throw new ResourceNotFoundException(ex.getMessage());
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
           throw new ResourceNotFoundException(ex.getMessage());
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
            throw new BadRequestException("Couldn't delete the Employee record. If the id is correct you need to delete all the attendance record first. Also if he is a manager you need to assign the department to another manager and update the records of the managed employees");
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
                throw new BadRequestException("No such field: " + field);
            }
        }).toList();

        for(Field declaredField : EmployeeDTO.class.getDeclaredFields()){
            if(!filterFields.contains(declaredField)){
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(employee, null);
                } catch (IllegalAccessException e) {
                    throw new InternalServerErrorException("could not apply filter to employee");
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
