package shop.taeheoki.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static shop.taeheoki.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static shop.taeheoki.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);

    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

//    @Test
//    void constructorNullCheck() {
//        assertThatThrownBy(() -> Member.create(null, "taeheoki", "secret", passwordEncoder))
//                .isInstanceOf(NullPointerException.class);
//    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        Assertions.assertThat(member.getNickname()).isEqualTo("Taeheoki");

        member.changeNickname("Charlie");

        Assertions.assertThat(member.getNickname()).isEqualTo("Charlie");
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret", passwordEncoder);

        Assertions.assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        Assertions.assertThat(member.isActive()).isFalse();

        member.activate();

        Assertions.assertThat(member.isActive()).isTrue();

        member.deactivate();

        Assertions.assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        Assertions.assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }
}