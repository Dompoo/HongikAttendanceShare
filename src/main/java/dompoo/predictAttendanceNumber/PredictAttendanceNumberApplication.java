package dompoo.predictAttendanceNumber;

import dompoo.predictAttendanceNumber.domain.Number;
import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@SpringBootApplication
public class PredictAttendanceNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredictAttendanceNumberApplication.class, args);
	}

	@Configuration
	@RequiredArgsConstructor
	public class testDataInit {
		private final NumberRepository numberRepository;
		private final TransactionRepository transactionRepository;

		@PostConstruct
		public void init() {
			numberRepository.save(Number.builder()
					.number(1234)
					.classNum("1")
					.addTime(LocalDateTime.now())
					.build());
			transactionRepository.save(Number.builder()
					.number(1234)
					.classNum("1")
					.addTime(LocalDateTime.now())
					.build());
			numberRepository.save(Number.builder()
					.number(5678)
					.classNum("2")
					.addTime(LocalDateTime.now())
					.build());
			transactionRepository.save(Number.builder()
					.number(5678)
					.classNum("2")
					.addTime(LocalDateTime.now())
					.build());
		}
	}

}
