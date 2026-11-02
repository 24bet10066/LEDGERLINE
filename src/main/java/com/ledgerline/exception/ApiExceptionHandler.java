package com.ledgerline.exception;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(BusinessException.class) ResponseEntity<Map<String,String>> business(BusinessException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(Exception.class) ResponseEntity<Map<String,String>> unexpected(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Request could not be completed"));}
}
