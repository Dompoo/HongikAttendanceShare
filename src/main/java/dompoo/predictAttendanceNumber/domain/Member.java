package dompoo.predictAttendanceNumber.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private int point;

    @OneToMany(mappedBy = "member")
    private List<TransactionNumber> numbers = new ArrayList<>();

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.point = 5;
    }
}
