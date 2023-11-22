package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionNumber, Long> {

    Optional<Integer> findByClassNum(String classNum);
}
