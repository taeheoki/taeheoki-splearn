package shop.taeheoki.splearn.domain;

import org.junit.jupiter.api.BeforeEach;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "Taeheoki", "secret");
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("taeheoki@splearn.app");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
