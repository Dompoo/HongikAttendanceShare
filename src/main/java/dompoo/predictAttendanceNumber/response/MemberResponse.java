package dompoo.predictAttendanceNumber.response;

import dompoo.predictAttendanceNumber.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponse {

    private String username;

    @Builder
    public MemberResponse(String username) {
        this.username = username;
    }

    public MemberResponse(Member member) {
        this.username = member.getUsername();
    }
}
