package br.com.galleriabank.desafio.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}
