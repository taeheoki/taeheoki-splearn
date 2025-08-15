package shop.taeheoki.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberTest {
    Member member;

    @BeforeEach
    void setUp() {
        member = Member.create("taeheoki@splearn.app", "Taeheoki", "secret", new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return "";
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return false;
            }
        });
    }


    @Test
    void createMember() {
        var member = Member.create("taeheoki@splearn.app", "taeheoki", "secret", null);

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> Member.create(null, "taeheoki", "secret", null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        Member member = Member.create("taeheoki", "Taeheoki", "secret", null);
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        Member member = Member.create("taeheoki", "Taeheoki", "secret", null);

        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        Member member = Member.create("taeheoki", "Taeheoki", "secret", null);

        member.activate();
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        Member member = Member.create("taeheoki", "Taeheoki", "secret", null);

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }
}