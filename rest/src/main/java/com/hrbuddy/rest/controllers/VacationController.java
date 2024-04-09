package com.hrbuddy.rest.controllers;

import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.VacationService;
import com.hrbuddy.services.dto.VacationDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;


@Path("/vacations")
public class VacationController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllVacations(@QueryParam("employeeId") int employeeId, @Context HttpHeaders headers) {
        SecurityManager.authorizeUser(headers);

        List<VacationDTO> vacations = VacationService.getAllVacations(employeeId);
        GenericEntity<List<VacationDTO>> entity = new GenericEntity<>(vacations){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getVacation(@PathParam("id") int id, @Context HttpHeaders headers) {
        SecurityManager.authorizeUser(headers);

        VacationDTO vacation = VacationService.getVacation(id);

        return Response.ok().entity(vacation).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createVacation(VacationDTO vacation, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

         VacationDTO createdVacation = VacationService.createVacation(vacation);

         return Response.status(Response.Status.CREATED).entity(createdVacation).build();

    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateVacation(VacationDTO vacation, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        VacationDTO updatedVacation = VacationService.updateVacation(vacation);

        return Response.ok().entity(updatedVacation).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteVacation(@PathParam("id") int id, @Context HttpHeaders header) {
        SecurityManager.authorizeAdmin(header);

        VacationService.deleteVacation(id);

        return Response.ok().entity("Vacation with id: " + id + " was deleted successfully").build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteVacationsByEmployeeId(@QueryParam("employeeId") int employeeId, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        VacationService.deleteVacationsByEmployeeId(employeeId);

        return Response.ok().entity("All vacation records for employee with id: " + employeeId + " were deleted successfully").build();
    }
}
