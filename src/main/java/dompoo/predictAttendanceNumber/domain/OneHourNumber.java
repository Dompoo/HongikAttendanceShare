package dompoo.predictAttendanceNumber.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneHourNumber {

    @Id
    @GeneratedValue
    private Long id;

    private int number;

    private String classNum;

    @Builder
    public OneHourNumber(int number, String classNum) {
        this.number = number;
        this.classNum = classNum;
    }
}
