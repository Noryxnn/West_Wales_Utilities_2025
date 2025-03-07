package uk.ac.cf.spring.client_project.security;

import org.junit.jupiter.api.Test;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QREncryptionUtilsTests {

    @Test
    void shouldSuccessfullyEncryptAndDecrypt()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String input = "Hello World";
        String cipherText = QREncryptionUtils.encrypt(input);
        String plainText = QREncryptionUtils.decrypt(cipherText);
        assertEquals(input, plainText);
    }
}
