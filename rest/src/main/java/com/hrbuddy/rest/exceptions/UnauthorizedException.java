package com.hrbuddy.rest.exceptions;

import com.hrbuddy.rest.exceptions.response.ErrorDetails;
import com.hrbuddy.rest.exceptions.response.ErrorMessage;
import jakarta.ws.rs.WebApplicationException;

import jakarta.ws.rs.core.Response;


public class UnauthorizedException extends WebApplicationException {

    public UnauthorizedException(String message) {

        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorDetails(ErrorMessage.UNAUTHORIZED.name(), 401, message))
                .build());
    }
}
