package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final NumberRepository numberRepository;

    /**
     * Transaction 레포지토리를 전체 조회하며
     * 중복값이 있는 Number 객체만을 Number 레포지토리에 넣는다.
     */
    public void refreshTransaction() {
        //TODO 최적화 방법 찾기
        List<TransactionNumber> numberList = transactionRepository.findAll();

        for (TransactionNumber number : numberList) {
            for (TransactionNumber compNumber : numberList) {
                if (isDuplicated(number, compNumber)) {
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
    public void deleteOldNumber() {
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
        refreshTransaction();
        transactionRepository.deleteAll();
    }

    private static boolean isDuplicated(TransactionNumber number, TransactionNumber compNumber) {
        return number.getNumber() == compNumber.getNumber()
                && number.getClassNum().equals(compNumber.getClassNum())
                && !number.getId().equals(compNumber.getId());
    }
}
