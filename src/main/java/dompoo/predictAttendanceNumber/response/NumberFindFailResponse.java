package dompoo.predictAttendanceNumber.response;

import dompoo.predictAttendanceNumber.domain.Number;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberFindFailResponse implements NumberResponse {

    private String classNum;

    @Builder
    public NumberFindFailResponse(String classNum) {
        this.classNum = classNum;
    }

    public NumberFindFailResponse(Number number) {
        this.classNum = number.getClassNum();
    }

}
