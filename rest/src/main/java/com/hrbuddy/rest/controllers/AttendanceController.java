package com.hrbuddy.rest.controllers;


import com.hrbuddy.rest.messages.ErrorResponse;
import com.hrbuddy.rest.messages.ResponseMessage;
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
    public Response getAllAttendanceRecords() {
        List<AttendanceDTO> attendances = AttendanceService.getAllAttendanceRecords();
        if(attendances.isEmpty()){
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.NOT_FOUND.name())
                    .code(404)
                    .description("No attendance records found")
                    .build();
            Response response =
                    Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
            throw new WebApplicationException(response);
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
                    .description("Couldn't create the attendance record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
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
                    .description("Couldn't update the attendance record. Maybe invalid format")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            throw new WebApplicationException(response);
        }
    }


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAttendanceRecord(@PathParam("id") int id) {
        try {
            AttendanceService.deleteAttendanceRecord(id);
            return Response.ok().entity("Attendance record was deleted successfully").build();
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse
                    .builder()
                    .message(ResponseMessage.BAD_REQUEST.name())
                    .code(400)
                    .description("Couldn't delete the Attendance record. Invalid ID.")
                    .build();
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
            throw new WebApplicationException(response);
        }
    }
}
