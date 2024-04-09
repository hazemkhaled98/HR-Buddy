package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.response.ErrorDetails;
import com.hrbuddy.rest.exceptions.response.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class IllegalArugmentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .message(ErrorMessage.BAD_REQUEST.name())
                .code(400)
                .description(exception.getMessage())
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(errorDetails).build();
    }
}
