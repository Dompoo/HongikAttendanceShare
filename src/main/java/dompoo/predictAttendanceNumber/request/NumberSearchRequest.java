package dompoo.predictAttendanceNumber.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberSearchRequest {

    private String classNum;

    public NumberSearchRequest(String classNum) {
        this.classNum = classNum;
    }
}
