package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.OneHourNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<OneHourNumber, Long> {

    Optional<Integer> findByClassNum(String classNum);
}
