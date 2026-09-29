package com.zuk.conference.auxiliary;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * Hashes passwords with BCrypt. Hashes created by older versions of the app
 * (unsalted MD5) are still accepted so they can be upgraded on the next login.
 */
@Component
public class PasswordHasher {

    /** BCrypt only uses the first 72 bytes of the input. */
    public static final int MAX_PASSWORD_BYTES = 72;

    private static final Pattern LEGACY_MD5 = Pattern.compile("[0-9a-f]{32}");

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null) {
            return false;
        }
        if (isLegacy(storedHash)) {
            String md5 = DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8));
            return MessageDigest.isEqual(md5.getBytes(StandardCharsets.US_ASCII),
                    storedHash.getBytes(StandardCharsets.US_ASCII));
        }
        if (!isAcceptableLength(rawPassword)) {
            return false;
        }
        return encoder.matches(rawPassword, storedHash);
    }

    public boolean isLegacy(String storedHash) {
        return storedHash != null && LEGACY_MD5.matcher(storedHash).matches();
    }

    public boolean isAcceptableLength(String rawPassword) {
        return rawPassword.getBytes(StandardCharsets.UTF_8).length <= MAX_PASSWORD_BYTES;
    }
}
