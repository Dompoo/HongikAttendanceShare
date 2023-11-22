package dompoo.predictAttendanceNumber.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberCreateRequest {

    private int number;
    private String classNum;

    @Builder
    public NumberCreateRequest(int number, String classNum) {
        this.number = number;
        this.classNum = classNum;
    }
}
