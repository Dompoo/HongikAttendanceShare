package dompoo.predictAttendanceNumber.exception.number;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class NumberException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public NumberException(String message) {
        super(message);
    }

    public NumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public abstract String statusCode();
}
