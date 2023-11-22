package dompoo.predictAttendanceNumber.response;

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
    public NumberResponse(Long id, int number, String classNum) {
        this.id = id;
        this.number = number;
        this.classNum = classNum;
    }
}
