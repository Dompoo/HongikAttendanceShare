package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import dompoo.predictAttendanceNumber.request.NumberCreateRequest;
import dompoo.predictAttendanceNumber.request.NumberSearchRequest;
import dompoo.predictAttendanceNumber.response.NumberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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

    /**
     * Number 레포지토리에서 원하는 출결번호가 있는지 찾고 리턴한다. (확정정보)
     */
    public NumberResponse getNumber(NumberSearchRequest request) {
        // 확정된 레포지토리에 같은 수업의 정보가 있는지 검색
        Number findNumber = numberRepository.findByClassNum(request.getClassNum())
                .orElseThrow();

        return NumberResponse.builder()
                .id(findNumber.getId())
                .number(findNumber.getNumber())
                .classNum(findNumber.getClassNum())
                .build();
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
     */
    public void createNumber(NumberCreateRequest request) {
        transactionRepository.save(TransactionNumber.builder()
                .number(request.getNumber())
                .classNum(request.getClassNum())
                .build());
    }

    /**
     * Transaction 레포지토리를 전체 조회하며
     * 중복값이 있는 Number 객체만을 Number 레포지토리에 넣는다.
     */
    public void transactionRefresh() {
        //TODO 최적화 방법 찾기
        List<TransactionNumber> numberList = transactionRepository.findAll();

        for (TransactionNumber number : numberList) {
            for (TransactionNumber compNumber : numberList) {
                if (number.getNumber() == compNumber.getNumber() &&
                        number.getClassNum().equals(compNumber.getClassNum())) {
                    //동일한 정보가 존재한다는 뜻.
                    numberRepository.save(Number.builder()
                            .classNum(number.getClassNum())
                            .number(number.getNumber())
                            .build());
                    transactionRepository.delete(number);
                    numberList.remove(number);
                }
            }
        }
    }

    /**
     * Number 레포지토리를 전체 조회하며 등록된지 30분이 지난 출결번호를 삭제한다.
     * 즉, 의미없는 정보를 삭제한다.
     */
    @Scheduled(cron = "0 */30 * * * *") // 30분마다 실행
    public void numberRefresh() {
        //TODO 최적화 방법 찾기
        List<Number> numberList = numberRepository.findAll();
        //현재 날짜
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        for (Number number : numberList) {
            //Number 객체 등록 날짜
            LocalDateTime addTime = number.getAddTime().truncatedTo(ChronoUnit.HOURS);

            // 1시간이 지났다면 레포지토리에서 삭제
            if (now.compareTo(addTime) >= 1) {
                numberRepository.delete(number);
            }
        }
    }

    /**
     * Transaction 레포지토리를 비운다.
     */
    @Scheduled(cron = "0 30 * * * *") // 매시간 30분에 실행
    public void emptyTransaction() {
        transactionRefresh();
        transactionRepository.deleteAll();
    }
}
