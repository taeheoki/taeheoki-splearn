package shop.taeheoki.splearn.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Getter
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    //    @Getter(AccessLevel.NONE)
    private MemberStatus status;

    private Member(String email, String nickname, String passwordHash) {
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);

        this.status = MemberStatus.PENDING;
    }

    public static Member create(String mail, String taeheoki, String password, PasswordEncoder passwordEncoder) {
        return new Member(mail, taeheoki, passwordEncoder.encode(password));
    }

    public void activate() {
        state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }
}
