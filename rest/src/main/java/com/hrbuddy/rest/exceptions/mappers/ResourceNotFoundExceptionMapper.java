package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.response.ErrorResponse;
import com.hrbuddy.rest.exceptions.ResourceNotFoundException;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ResponseMessage.NOT_FOUND.name())
                .code(404)
                .description(e.getMessage())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
    }
}
