package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
import com.hrbuddy.services.AttendanceService;
import com.hrbuddy.services.dto.AttendanceDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/attendances")
public class AttendanceController {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAttendanceRecords(@QueryParam("employeeId") int employeeId) {
        List<AttendanceDTO> attendances = AttendanceService.getAllAttendanceRecords(employeeId);
        if(attendances.isEmpty()){
            throw new ResourceNotFoundException("No attendance records found");
        }
        GenericEntity<List<AttendanceDTO>> entity = new GenericEntity<>(attendances){};
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAttendanceRecord(@PathParam("id") int id) {
        Optional<AttendanceDTO> attendance = AttendanceService.getAttendanceRecord(id);
        if(attendance.isEmpty()){
            throw new ResourceNotFoundException("No attendance record found for id: " + id);
        }
        return Response.ok(attendance.get()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createAttendanceRecord(AttendanceDTO attendance) {
        try {
            AttendanceDTO createdAttendance = AttendanceService.createAttendance(attendance);
            return Response.status(Response.Status.CREATED).entity(createdAttendance).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateAttendanceRecord(AttendanceDTO attendance) {
        try {
            AttendanceDTO updatedAttendance = AttendanceService.updateAttendanceRecord(attendance);
            return Response.ok().entity(updatedAttendance).build();
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
    }



    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAttendanceRecord(@PathParam("id") int id) {
        AttendanceService.deleteAttendanceRecord(id);
        return Response.ok().entity("Attendance record was deleted successfully").build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAllAttendanceRecords(@QueryParam("employeeId") int employeeId) {
        if(employeeId == 0){
            throw new BadRequestException("Employee id is required");
        }
        AttendanceService.deleteAllAttendanceByEmployeeId(employeeId);
        return Response.ok().entity("All attendance records for employee with id: " + employeeId + " were deleted successfully").build();
    }
}
