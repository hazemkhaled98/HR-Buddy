package com.hrbuddy.rest.controllers;

import com.hrbuddy.rest.beans.EmployeesFilters;
import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.EmployeeService;
import com.hrbuddy.services.dto.EmployeeDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;


@Path("/employees")
public class EmployeeController {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllEmployees(@BeanParam EmployeesFilters filters, @Context HttpHeaders headers) {
        SecurityManager.authorizeUser(headers);
        List<EmployeeDTO> employees = EmployeeService.getAllEmployees(filters.getDepartmentId(), filters.getJobId(), filters.getManagerId(), filters.getOffset(), filters.getOffset());


        filterEmployees(employees, filters.getFieldsParam());

        GenericEntity<List<EmployeeDTO>> entity = new GenericEntity<>(employees) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployee(@PathParam("id") int id, @QueryParam("fields") String fieldsParam, @Context HttpHeaders headers) {
        SecurityManager.authorizeUser(headers);

        EmployeeDTO employee = EmployeeService.getEmployee(id);
        filterEmployee(employee, fieldsParam);

        return Response.ok(employee).build();
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createEmployee(EmployeeDTO employee, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        EmployeeDTO createdEmployee = EmployeeService.createEmployee(employee);

        return Response.status(Response.Status.CREATED).entity(createdEmployee).build();

    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateEmployee(EmployeeDTO employee, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        EmployeeDTO updatedEmployee = EmployeeService.updateEmployee(employee);

        return Response.ok().entity(updatedEmployee).build();
    }


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteEmployee(@PathParam("id") int id, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        EmployeeService.deleteEmployee(id);

        return Response.ok().entity("Employee record was deleted successfully").build();
    }

    private void filterEmployee(EmployeeDTO employee, String fieldsParams){
        if(fieldsParams == null || fieldsParams.isEmpty())
            return;
        List<String> fields = Arrays.asList(fieldsParams.split(","));
        List<Field> filterFields = fields.stream().map(field -> {
            try {
                return EmployeeDTO.class.getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("No such field: " + field);
            }
        }).toList();

        for(Field declaredField : EmployeeDTO.class.getDeclaredFields()){
            if(!filterFields.contains(declaredField)){
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(employee, null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("could not apply filter to employee");
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
