package dompoo.predictAttendanceNumber.response;

import dompoo.predictAttendanceNumber.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponse {

    private String username;
    private String password;
    private int point;

    public MemberResponse(String username, String password, int point) {
        this.username = username;
        this.password = password;
        this.point = point;
    }

    public MemberResponse(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this. point = member.getPoint();
    }
}
