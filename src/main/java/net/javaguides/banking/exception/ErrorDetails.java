package net.javaguides.banking.exception;

public record ErrorDetails(java.time.LocalDateTime timestamp, String message, String details, String errorcode)
{

}

