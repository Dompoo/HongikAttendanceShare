package dompoo.predictAttendanceNumber.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberCreateRequest {

    @Size(max = 9999, min = 0)
    private int number;

    @NotBlank(message = "학수번호는 필수 입력 사항입니다.")
    private String classNum;

    @Builder
    public NumberCreateRequest(int number, String classNum) {
        this.number = number;
        this.classNum = classNum;
    }
}
