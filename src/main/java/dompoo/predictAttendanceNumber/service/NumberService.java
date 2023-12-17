package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Member;
import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import dompoo.predictAttendanceNumber.repository.MemberRepository;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberFindSuccessResponse;
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
    public Optional<NumberFindSuccessResponse> getNumber(NumberSearchRequest request) {
        // 확정된 레포지토리에 같은 수업의 정보가 있는지 검색
        return numberRepository.findByClassNum(request.getClassNum())
                .map(NumberFindSuccessResponse::new);
    }

    /**
     * 이미 등록된 정보인지를 체크한다.
     */
    public Boolean isPresent(String classNum) {
        // 확정된 레포지토리에 같은 수업의 정보가 있는지 검색
        Optional<Number> findNumber = numberRepository.findByClassNum(classNum);
        return findNumber.isPresent();
    }

    /**
     * request의 정보를 받아 Number 객체로
     * Transaction 레포지토리에 저장한다.
     * 만약 Transaction 레포지토리에 중복된 정보가 있다면(검증됨),
     * 해당 정보를 영구 레포지토리로 옮긴다.
     */
    public void createNumber(NumberCreateRequest request, String loginUserName) {
        Optional<TransactionNumber> findNumber = transactionRepository.findByClassNumAndNumber(request.getClassNum(), request.getNumber());
        Member loginMember = memberRepository.findByUsername(loginUserName).orElseThrow();

        if (findNumber.isEmpty()) {
            //레포지토리내 중복 없음
            transactionRepository.save(TransactionNumber.builder()
                    .number(request.getNumber())
                    .classNum(request.getClassNum())
                    .member(loginMember)
                    .build());
        } else {
            //레포지토리에 중복이 있다.
            //중복 멤버가 현재 멤버와 같은지 체크, 같다면 중복 저장이므로 return
            //같지 않다면 영구레포지토리로 이동
            TransactionNumber transactionNumber = findNumber.get();
            if (transactionNumber.getMember().getUsername().equals(loginUserName)) {
                return;
            }

            transactionRepository.delete(transactionNumber);
            numberRepository.save(Number.builder()
                    .classNum(request.getClassNum())
                    .number(request.getNumber())
                    .build());
        }
    }
}
