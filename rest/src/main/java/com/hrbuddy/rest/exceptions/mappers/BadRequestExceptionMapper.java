package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.BadRequestException;
import com.hrbuddy.rest.exceptions.response.ErrorResponse;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException exception) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ResponseMessage.BAD_REQUEST.name())
                .code(400)
                .description(exception.getMessage())
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
