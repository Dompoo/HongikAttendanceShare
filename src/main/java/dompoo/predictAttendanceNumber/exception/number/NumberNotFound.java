package dompoo.predictAttendanceNumber.exception.number;

public class NumberNotFound extends NumberException {

    private static final String MESSAGE = "존재하지 않는 채팅방입니다.";
    private static final String STATUS_CODE = "404";

    public NumberNotFound() {
        super(MESSAGE);
    }

    public NumberNotFound(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public String statusCode() {
        return STATUS_CODE;
    }
}
