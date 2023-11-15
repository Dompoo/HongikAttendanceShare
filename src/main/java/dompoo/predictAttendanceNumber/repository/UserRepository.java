package dompoo.predictAttendanceNumber.repository;

import dompoo.predictAttendanceNumber.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
