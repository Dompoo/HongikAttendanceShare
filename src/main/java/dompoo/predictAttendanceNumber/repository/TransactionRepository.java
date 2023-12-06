package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.TransactionNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionNumber, Long> {

}
