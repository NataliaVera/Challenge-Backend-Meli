package com.challengemeli.api.helper;

import com.challengemeli.model.exception.InvalidInputException;
import com.challengemeli.model.exception.ResourceAlreadyExistsException;
import com.challengemeli.model.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HandleException {

    public Mono<ServerResponse> handleException(Throwable ex) {
        HttpStatus status;
        String errorType;

        if (ex instanceof InvalidInputException) {
            status = HttpStatus.BAD_REQUEST;
            errorType = "Invalid input";
        } else if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorType = "Not found";
        } else if (ex instanceof ResourceAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
            errorType = "Conflict";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorType = "Server error";
        }

        CustomErrorResponse errorResponse = new CustomErrorResponse(errorType, ex.getMessage());

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }


}
