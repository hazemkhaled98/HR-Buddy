package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.response.ErrorDetails;
import com.hrbuddy.rest.exceptions.response.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .message(ErrorMessage.INTERNAL_SERVER_ERROR.name())
                .code(500)
                .description("Server faced issues fulfilling your request")
                .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorDetails).build();
    }
}
