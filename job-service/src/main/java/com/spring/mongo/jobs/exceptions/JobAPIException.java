package com.spring.mongo.jobs.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class JobAPIException extends RuntimeException {
    private HttpStatus status;

    public JobAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
