package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.response.ErrorDetails;
import com.hrbuddy.rest.exceptions.response.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.NoSuchElementException;


@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(NoSuchElementException e) {
        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .message(ErrorMessage.NOT_FOUND.name())
                .code(404)
                .description(e.getMessage())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(errorDetails).build();
    }
}
