package com.example.ExceptionHandler;

import com.example.ResponseDTOs.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest req){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "not found",
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(EntityNotFoundException e, HttpServletRequest req){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "not found",
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(NullPointerException e, HttpServletRequest req){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Null value",
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(HttpClientErrorException.Unauthorized e, HttpServletRequest req){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

    // 🌟 ADD THIS FALLBACK METHOD
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllOtherExceptions(Exception e, HttpServletRequest req) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                //e.getClass().getSimpleName() + " : " + e.getMessage(), // This tells you exactly what error leaked!
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
