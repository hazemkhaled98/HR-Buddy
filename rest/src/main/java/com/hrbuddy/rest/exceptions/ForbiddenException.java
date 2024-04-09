package com.hrbuddy.rest.exceptions;

import com.hrbuddy.rest.exceptions.response.ErrorDetails;
import com.hrbuddy.rest.exceptions.response.ResponseMessage;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ForbiddenException extends WebApplicationException {

    public ForbiddenException(String message) {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity(new ErrorDetails(ResponseMessage.FORBIDDEN.name(), 403, message))
                .build());
    }
}
