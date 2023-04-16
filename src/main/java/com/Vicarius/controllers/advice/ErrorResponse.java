package com.Vicarius.controllers.advice;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String timestamp;
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        this.status = status;
        this.message = message;
    }
}