package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Member;
import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import dompoo.predictAttendanceNumber.repository.MemberRepository;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberFindFailResponse;
import dompoo.predictAttendanceNumber.response.NumberFindSuccessResponse;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NumberService {

    private final NumberRepository numberRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Number 레포지토리에서 원하는 출결번호가 있는지 찾고 리턴한다. (확정정보)
     */
    @Transactional
    public NumberResponse getNumber(NumberSearchRequest request, String username) {
        Optional<Number> findNumber = numberRepository.findByClassNum(request.getClassNum());

        //찾는 출결번호가 있으면 현재 로그인된 사용자의 포인트를 -1한다.
        if (findNumber.isPresent()) {
            Member loginMember = memberRepository.findByUsername(username)
                    .orElseThrow(EntityNotFoundException::new);
            loginMember.addPoint(-1);
            return new NumberFindSuccessResponse(findNumber.get());
        }
        //찾는 출결번호가 없으면 그냥 리턴한다.
        return new NumberFindFailResponse(request.getClassNum());
    }

    /**
     * request의 정보를 받아 Number 객체로
     * Transaction 레포지토리에 저장한다.
     * 만약 Transaction 레포지토리에 중복된 정보가 있다면(검증됨),
     * 해당 정보를 영구 레포지토리로 옮긴다.
     */
    public void createNumber(NumberCreateRequest request, String loginUsername) {
        Optional<TransactionNumber> findNumber = transactionRepository.findByClassNumAndNumber(request.getClassNum(), request.getNumber());
        Member loginMember = memberRepository.findByUsername(loginUsername).orElseThrow();

        //같은 정보 중복이 있다.
        if (findNumber.isPresent()) {
            TransactionNumber transactionNumber = findNumber.get();
            String savedUsername = transactionNumber.getMember().getUsername();
            //중복된 정보의 작성자가 현재 로그인된 멤버와 같은지 체크
            //같다면 정책상 중복저장이므로 return
            if (savedUsername.equals(loginUsername)) {
                return;
            }
            //같지 않다면 valid한 정보 -> 작성자의 포인트 추가 후 영구레포지토리로 이동
            memberRepository.findByUsername(savedUsername)
                    .orElseThrow(EntityNotFoundException::new)
                    .addPoint(1);
            memberRepository.findByUsername(loginUsername)
                    .orElseThrow(EntityNotFoundException::new)
                    .addPoint(1);

            transactionRepository.delete(transactionNumber);
            numberRepository.save(Number.builder()
                    .classNum(request.getClassNum())
                    .number(request.getNumber())
                    .build());
        }

        //레포지토리내 중복 없음
        transactionRepository.save(TransactionNumber.builder()
                .number(request.getNumber())
                .classNum(request.getClassNum())
                .member(loginMember)
                .build());
    }
}
