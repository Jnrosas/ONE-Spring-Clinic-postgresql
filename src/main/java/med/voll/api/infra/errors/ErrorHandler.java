package med.voll.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

   //This project is using Optionals in Get by Id, therefore no exceptions are triggered
   @ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity handleError404() {
      return ResponseEntity.notFound().build();
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity handleError400(MethodArgumentNotValidException e) {
      var errors = e.getFieldErrors().stream()
            .map(ShowErrorDto::new).collect(Collectors.toList());
      return ResponseEntity.badRequest().body(errors);
   }

   private record ShowErrorDto(String field, String error) {
      public ShowErrorDto(FieldError error) {
         this(error.getField(), error.getDefaultMessage());
      }
   }
}
