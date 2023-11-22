package dompoo.predictAttendanceNumber.response;

import dompoo.predictAttendanceNumber.domain.Number;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberResponse {

    private Long id;

    private int number;

    private String classNum;

    @Builder
    public NumberResponse(int number, String classNum) {
        this.number = number;
        this.classNum = classNum;
    }

    @Builder
    public NumberResponse(Number number) {
        this.number = number.getNumber();
        this.classNum = number.getClassNum();
    }
}
