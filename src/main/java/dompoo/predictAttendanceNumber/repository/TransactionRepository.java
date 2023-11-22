package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.Number;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Number, Long> {

    Optional<Number> findByClassNum(String classNum);
}
