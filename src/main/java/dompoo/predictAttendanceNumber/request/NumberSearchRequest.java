package dompoo.predictAttendanceNumber.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberSearchRequest {

    @NotBlank(message = "학수번호는 필수 입력 사항입니다.")
    private String classNum;

    public NumberSearchRequest(String classNum) {
        this.classNum = classNum;
    }
}
