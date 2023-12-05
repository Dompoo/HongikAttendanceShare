package dompoo.predictAttendanceNumber.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberCreateRequest {

    @Max(value = 9999, message = "출결번호는 4자리 입니다.")
    private int number;

    @NotBlank(message = "학수번호는 필수 입력 사항입니다.")
    private String classNum;

    @Builder
    public NumberCreateRequest(int number, String classNum) {
        this.number = number;
        this.classNum = classNum;
    }
}
