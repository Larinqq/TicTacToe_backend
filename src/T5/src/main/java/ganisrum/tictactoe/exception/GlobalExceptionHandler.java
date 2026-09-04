package ganisrum.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleGameNotFound(GameNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Game Not Found");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFound(UserNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
        problemDetail.setTitle("User Not Found");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExist(UserAlreadyExistException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage()
        );
        problemDetail.setTitle("Registration Failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(IncorrectGameProvidedException.class)
    public ResponseEntity<ProblemDetail> handleIncorrectGameProvidedException(IncorrectGameProvidedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("Join failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(NotYourTurnException.class)
    public ResponseEntity<ProblemDetail> handleNotYourTurnException(NotYourTurnException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, ex.getMessage()
        );
        problemDetail.setTitle("Move failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }


    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<ProblemDetail> handleNoAccessException(NoAccessException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, ex.getMessage()
        );
        problemDetail.setTitle("Access denied");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }


    @ExceptionHandler(IncorrectMoveProvidedException.class)
    public ResponseEntity<ProblemDetail> handleIncorrectMoveProvidedException(IncorrectMoveProvidedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("Move failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed: " + errorMessage
        );
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("timestamp", Instant.now());


        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid request body format"
        );
        problemDetail.setTitle("Malformed JSON");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ProblemDetail> handleInvalidTokenException(InvalidTokenException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Invalid or expired token"
        );
        problemDetail.setTitle("Authentication Failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Somethings went wrong"
        );
        problemDetail.setTitle("Internal server error");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(IncorrectDataProvided.class)
    public ResponseEntity<ProblemDetail> handleIncorrectDataProvided(IncorrectDataProvided ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("Request failed");
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
