package com.hrbuddy.rest.controllers;

import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
import com.hrbuddy.services.VacationService;
import com.hrbuddy.services.dto.VacationDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;


@Path("/vacations")
public class VacationController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllVacations(@QueryParam("employeeId") int employeeId) {
        List<VacationDTO> vacations = VacationService.getAllVacations(employeeId);
        if(vacations.isEmpty()){
            throw new ResourceNotFoundException("No vacations found");
        }
        GenericEntity<List<VacationDTO>> entity = new GenericEntity<>(vacations){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getVacation(@PathParam("id") int id) {
        Optional<VacationDTO> vacation = VacationService.getVacation(id);
        if(vacation.isEmpty()){
            throw new ResourceNotFoundException("No vacation found for id: " + id);
        }
        return Response.ok().entity(vacation.get()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createVacation(VacationDTO vacation) {
        try {
         VacationDTO createdVacation = VacationService.createVacation(vacation);
            return Response.status(Response.Status.CREATED).entity(createdVacation).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateVacation(VacationDTO vacation) {
        try {
            VacationDTO updatedVacation = VacationService.updateVacation(vacation);
            return Response.ok().entity(updatedVacation).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteVacation(@PathParam("id") int id) {
        VacationService.deleteVacation(id);
        return Response.ok().entity("Vacation with id: " + id + " was deleted successfully").build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteVacationsByEmployeeId(@QueryParam("employeeId") int employeeId) {
        if(employeeId == 0){
            throw new BadRequestException("Employee id is required");
        }
        VacationService.deleteVacationsByEmployeeId(employeeId);
        return Response.ok().entity("All vacation records for employee with id: " + employeeId + " were deleted successfully").build();
    }
}
