package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.ForbiddenException;
import com.hrbuddy.rest.exceptions.response.ErrorResponse;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {


    @Override
    public Response toResponse(ForbiddenException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ResponseMessage.FORBIDDEN.name())
                .code(403)
                .description(e.getMessage())
                .build();
        return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
    }
}
