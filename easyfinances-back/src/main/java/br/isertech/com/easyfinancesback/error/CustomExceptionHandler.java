package br.isertech.com.easyfinancesback.error;

import br.isertech.com.easyfinancesback.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomErrorResponse> customException(Exception e) {
//
//        CustomErrorResponse errors = CustomErrorResponse.builder().message(e.getMessage())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
//
//        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> userNotFound(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> roleNotFound(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> itemNotFound(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> roleAlreadyExists(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<CustomErrorResponse> operationFailed(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> userAlreadyExists(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> emailAlreadyExists(Exception e) {

        CustomErrorResponse errors = CustomErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}