package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.UnauthorizedException;
import com.hrbuddy.rest.exceptions.response.ErrorResponse;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ResponseMessage.UNAUTHORIZED.name())
                .code(401)
                .description(e.getMessage())
                .build();
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
    }
}
