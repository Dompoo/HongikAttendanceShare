package dompoo.predictAttendanceNumber.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "유저이름은 필수 입력 사항입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;

    @NotBlank(message = "확인비밀번호는 필수 입력 사항입니다.")
    private String checkPassword;

    @Builder
    public MemberCreateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
