package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.Number;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Number, Long> {

    List<Number> findByClassNum(String classNum);
}
