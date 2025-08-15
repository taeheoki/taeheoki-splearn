package shop.taeheoki.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        Email email1 = new Email("taeheoki@splearn.app");
        Email email2 = new Email("taeheoki@splearn.app");

        assertThat(email1).isEqualTo(email2);
    }
}