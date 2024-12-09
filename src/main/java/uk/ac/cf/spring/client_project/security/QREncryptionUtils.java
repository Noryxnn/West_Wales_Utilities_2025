package uk.ac.cf.spring.client_project.security;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

// Encryption/decryption adapted from
// https://www.baeldung.com/java-aes-encryption-decryption
@UtilityClass
public class QREncryptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(QREncryptionUtils.class);
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    @Getter
    private final String secretKey = System.getenv("QR_ENCRYPTION_KEY");

    // Generate a random initialization vector to add unpredictability to the encryption process
    // Prevents patterns from being detected
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String input)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = generateIv();
        cipher.init(Cipher.ENCRYPT_MODE, decodeSecretKey(), iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());

        // Combine the IV and the encrypted data
        // Combining two byte arrays: https://stackoverflow.com/a/5683621
        byte[] combinedOutput = new byte[iv.getIV().length + cipherText.length];
        System.arraycopy(iv.getIV(), 0, combinedOutput, 0, iv.getIV().length);
        System.arraycopy(cipherText, 0, combinedOutput, iv.getIV().length, cipherText.length);

        return Base64.getEncoder()
                .encodeToString(combinedOutput);
    }

    public static String decrypt(String cipherText)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        byte[] encryptedData = Base64.getDecoder().decode(cipherText);

        // Get IV from the encrypted data
        byte[] ivBytes = new byte[16];
        System.arraycopy(encryptedData, 0, ivBytes, 0, ivBytes.length);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // Get the cipher text
        byte[] cipherBytes = new byte[encryptedData.length - ivBytes.length];
        System.arraycopy(encryptedData, ivBytes.length, cipherBytes, 0, cipherBytes.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, decodeSecretKey(), iv);
        byte[] plainText = cipher.doFinal(cipherBytes);

        return new String(plainText);
    }

    private static SecretKeySpec decodeSecretKey() {
        if (secretKey == null) {
            logger.error("Secret key not found when attempting to decrypt");
            throw new IllegalStateException("Environment variable QR_ENCRYPTION_KEY not found");
        }

        // Convert to SecretKey
        byte[] decodedKeyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKeyBytes, "AES");
    }

    public static boolean validateDecryptedData(Map<String, Object> decryptedData) {
        String secretKey = getSecretKey();
        return decryptedData.get("secretKey").equals(secretKey) && decryptedData.containsKey("userId") && decryptedData.containsKey("timestamp");
    }
}
