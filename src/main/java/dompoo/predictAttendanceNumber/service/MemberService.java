package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Member;
import dompoo.predictAttendanceNumber.repository.MemberRepository;
import dompoo.predictAttendanceNumber.request.MemberCreateRequest;
import dompoo.predictAttendanceNumber.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public MemberResponse registerMember(MemberCreateRequest request) {
        Member savedMember = memberRepository.save(Member.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build()
        );
        return new MemberResponse(savedMember);
    }
}
