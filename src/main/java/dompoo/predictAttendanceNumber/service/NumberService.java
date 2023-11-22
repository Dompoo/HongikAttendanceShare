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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NumberService {

    private final NumberRepository numberRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    /**
     *
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

    public void createNumber(NumberCreateRequest request) {
        transactionRepository.save(Number.builder()
                .number(request.getNumber())
                .classNum(request.getClassNum())
                .build());
    }
}
