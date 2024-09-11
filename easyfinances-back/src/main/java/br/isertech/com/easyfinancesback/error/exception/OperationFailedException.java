package br.isertech.com.easyfinancesback.error.exception;

import java.io.Serial;

public class OperationFailedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public OperationFailedException(String message) {
        super(message);
    }
}
