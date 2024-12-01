package uk.ac.cf.spring.client_project.security;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionUtilsTests {

    @Test
    void shouldSuccessfullyEncryptAndDecrypt()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String secretKey = System.getenv("QR_ENCRYPTION_KEY");

        String input = "Hello World";
        String cipherText = EncryptionUtils.encrypt(input, secretKey);
        String plainText = EncryptionUtils.decrypt(cipherText, secretKey);
        assertEquals(input, plainText);
    }
}
