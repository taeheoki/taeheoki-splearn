package shop.taeheoki.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public void setId(Long id) {
        this.id = id;
    }

    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();


        member.email = new Email(createRequest.mail());
        member.nickname = Objects.requireNonNull(createRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
