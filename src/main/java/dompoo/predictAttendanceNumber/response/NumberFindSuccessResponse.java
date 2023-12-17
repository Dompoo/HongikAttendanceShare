package dompoo.predictAttendanceNumber.response;

import dompoo.predictAttendanceNumber.domain.Number;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberFindSuccessResponse {

    private Long id;

    private int number;

    private String classNum;

    @Builder
    public NumberFindSuccessResponse(Long id, int number, String classNum) {
        this.id = id;
        this.number = number;
        this.classNum = classNum;
    }

    public NumberFindSuccessResponse(Number number) {
        this.id = number.getId();
        this.number = number.getNumber();
        this.classNum = number.getClassNum();
    }

}
