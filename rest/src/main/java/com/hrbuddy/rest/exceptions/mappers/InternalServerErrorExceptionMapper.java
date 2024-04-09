package com.hrbuddy.rest.exceptions.mappers;

import com.hrbuddy.rest.exceptions.response.ErrorResponse;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

//@Provider
//public class InternalServerErrorExceptionMapper implements ExceptionMapper<Exception> {
//    @Override
//    public Response toResponse(Exception e) {
//        ErrorResponse errorResponse = ErrorResponse
//                .builder()
//                .message(ResponseMessage.INTERNAL_SERVER_ERROR.name())
//                .code(500)
//                .description("Server faced issues fulfilling your request")
//                .build();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
//    }
//}
