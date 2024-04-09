package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.security.SecurityManager;
import com.hrbuddy.services.AttendanceService;
import com.hrbuddy.services.dto.AttendanceDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/attendances")
public class AttendanceController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAttendanceRecords(@QueryParam("employeeId") int employeeId, @Context HttpHeaders headers) {

        SecurityManager.authorizeUser(headers);

        List<AttendanceDTO> attendances = AttendanceService.getAllAttendanceRecords(employeeId);

        GenericEntity<List<AttendanceDTO>> entity = new GenericEntity<>(attendances){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAttendanceRecord(@PathParam("id") int id, @Context HttpHeaders headers) {

        SecurityManager.authorizeUser(headers);

        AttendanceDTO attendance = AttendanceService.getAttendanceRecord(id);

        return Response.ok(attendance).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createAttendanceRecord(AttendanceDTO attendance, @Context HttpHeaders headers) {
        SecurityManager.authorizeAdmin(headers);

        AttendanceDTO createdAttendance = AttendanceService.createAttendance(attendance);

        return Response.status(Response.Status.CREATED).entity(createdAttendance).build();
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateAttendanceRecord(AttendanceDTO attendance, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        AttendanceDTO updatedAttendance = AttendanceService.updateAttendanceRecord(attendance);

        return Response.ok().entity(updatedAttendance).build();
    }



    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAttendanceRecord(@PathParam("id") int id, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        AttendanceService.deleteAttendanceRecord(id);

        return Response.ok().entity("Attendance record was deleted successfully").build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAllAttendanceRecords(@QueryParam("employeeId") int employeeId, @Context HttpHeaders headers) {

        SecurityManager.authorizeAdmin(headers);

        AttendanceService.deleteAllAttendanceByEmployeeId(employeeId);

        return Response.ok().entity("All attendance records for employee with id: " + employeeId + " were deleted successfully").build();
    }
}
