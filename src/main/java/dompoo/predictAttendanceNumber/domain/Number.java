package dompoo.predictAttendanceNumber.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Number {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String classNum;

    private LocalDateTime addTime;

    @Builder
    public Number(int number, String classNum, LocalDateTime addTime) {
        this.number = number;
        this.classNum = classNum;
        this.addTime = addTime;
    }
}
