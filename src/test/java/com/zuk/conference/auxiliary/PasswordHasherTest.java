package com.zuk.conference.auxiliary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordHasherTest {

    private final PasswordHasher hasher = new PasswordHasher();

    @Test
    void bcryptRoundTrip() {
        String hash = hasher.hash("secret");
        assertThat(hash).startsWith("$2").isNotEqualTo("secret");
        assertThat(hasher.matches("secret", hash)).isTrue();
        assertThat(hasher.matches("wrong", hash)).isFalse();
        assertThat(hasher.isLegacy(hash)).isFalse();
    }

    @Test
    void acceptsLegacyMd5Hashes() {
        String md5OfAdmin = "21232f297a57a5a743894a0e4a801fc3";
        assertThat(hasher.isLegacy(md5OfAdmin)).isTrue();
        assertThat(hasher.matches("admin", md5OfAdmin)).isTrue();
        assertThat(hasher.matches("Admin", md5OfAdmin)).isFalse();
    }

    @Test
    void rejectsNullsAndTooLongPasswords() {
        String hash = hasher.hash("secret");
        assertThat(hasher.matches(null, hash)).isFalse();
        assertThat(hasher.matches("secret", null)).isFalse();
        assertThat(hasher.isAcceptableLength("x".repeat(72))).isTrue();
        assertThat(hasher.isAcceptableLength("x".repeat(73))).isFalse();
        assertThat(hasher.matches("x".repeat(100), hash)).isFalse();
    }
}
