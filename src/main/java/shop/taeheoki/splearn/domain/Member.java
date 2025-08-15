package shop.taeheoki.splearn.domain;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

import static org.springframework.util.Assert.state;

@Getter
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    //    @Getter(AccessLevel.NONE)
    private MemberStatus status;

    public Member() {
    }

    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
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
