package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Member;
import dompoo.predictAttendanceNumber.repository.MemberRepository;
import dompoo.predictAttendanceNumber.request.MemberCreateRequest;
import dompoo.predictAttendanceNumber.response.MemberResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void registerMember(MemberCreateRequest request) {
        memberRepository.save(Member.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build()
        );
    }

    public MemberResponse getMemberInfo(String username) {
        Member loginMember = memberRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return new MemberResponse(loginMember);
    }

    public boolean hasEnoughPoint(String username) {
        Member loginMember = memberRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return loginMember.getPoint() > 0;
    }
}
