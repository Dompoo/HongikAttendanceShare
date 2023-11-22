package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.exception.number.NumberNotFound;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import dompoo.predictAttendanceNumber.repository.UserRepository;
import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NumberService {

    private final NumberRepository numberRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    /**
     * 먼저 Transaction 레포지토리에서 원하는 출결번호가 있는지 검색한다. (최신정보)
     * Transaction 레포지토리에 값이 있다면 리턴하고,
     * 만약 없다면, Number 레포지토리에서 원하는 출결번호를 검색한다. (사용자가 Refresh 했을 경우)
     */
    public NumberResponse getNumber(NumberSearchRequest request) {
        Number findNumber;

        // 1시간 레포지토리에 같은 수업의 정보가 있는지 검색
        Optional<Number> transactionNumber = transactionRepository.findByClassNum(request.getClassNum());

        if (transactionNumber.isPresent()) {
            findNumber = transactionNumber.get();
        } else {
            // 영구 레포지토리에 같은 수업의 정보가 있는지 검색
            findNumber = numberRepository.findByClassNum(request.getClassNum())
                    .orElseThrow(NumberNotFound::new);
        }

        return NumberResponse.builder()
                .id(findNumber.getId())
                .number(findNumber.getNumber())
                .classNum(findNumber.getClassNum())
                .build();
    }

    /**
     * request의 정보를 받아 Number 객체로
     * Transaction 레포지토리에 저장합니다.
     */
    public void createNumber(NumberCreateRequest request) {
        transactionRepository.save(Number.builder()
                .number(request.getNumber())
                .classNum(request.getClassNum())
                .addTime(LocalDateTime.now())
                .build());
    }

    /**
     * Transaction 레포지토리를 전체 조회하며
     * 중복값이 있는 Number 객체만을 Number 레포지토리에 넣는다.
     */
    public void transactionRefresh() {
        //TODO 최적화 방법 찾기
        List<Number> numberList = transactionRepository.findAll();

        for (Number number : numberList) {
            for (Number compNumber : numberList) {
                if (number.getNumber() == compNumber.getNumber() &&
                        number.getClassNum().equals(compNumber.getClassNum())) {
                    //동일한 정보가 존재한다는 뜻.
                    numberRepository.save(number);
                    transactionRepository.delete(number);
                    numberList.remove(number);
                }
            }
        }
    }

    /**
     * Number 레포지토리를 전체 조회하며 24시간이 지난 출결번호를 삭제합니다.
     * 즉, 의미없는 정보를 삭제합니다.
     */
    public void numberRefresh() {
        //TODO 최적화 방법 찾기
        List<Number> numberList = numberRepository.findAll();
        //현재 날짜
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

        for (Number number : numberList) {
            //Number 객체 등록 날짜
            LocalDateTime addTime = number.getAddTime().truncatedTo(ChronoUnit.DAYS);

            // 24시간이 지났다면 레포지토리에서 삭제
            if (now.compareTo(addTime) >= 1) {
                numberRepository.delete(number);
            }
        }
    }

    /**
     * Transaction 레포지토리를 비웁니다.
     */
    public void emptyTransaction() {
        transactionRepository.deleteAll();
    }
}
