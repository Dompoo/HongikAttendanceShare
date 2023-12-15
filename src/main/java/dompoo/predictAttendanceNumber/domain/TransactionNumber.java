package dompoo.predictAttendanceNumber.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String classNum;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private Member member;

    @Builder
    public TransactionNumber(int number, String classNum, Member member) {
        this.number = number;
        this.classNum = classNum;
        setMember(member);
    }

    //연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getNumbers().add(this);
    }
}
