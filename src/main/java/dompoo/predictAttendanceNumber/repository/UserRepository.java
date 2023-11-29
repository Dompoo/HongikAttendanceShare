package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
}
