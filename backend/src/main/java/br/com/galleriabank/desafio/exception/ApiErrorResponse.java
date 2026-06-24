package br.com.galleriabank.desafio.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorResponse> fields
) {
    public ApiErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, List.of());
    }
}
