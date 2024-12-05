package uk.ac.cf.spring.client_project.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.experimental.UtilityClass;
import uk.ac.cf.spring.client_project.security.QREncryptionUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@UtilityClass
public class QRCodeGenerator {
    /**
     * Generate a QR code for the given text.
     * Adapted from https://medium.com/nerd-for-tech/how-to-generate-qr-code-in-java-spring-boot-134adb81f10d
     *
     * @param text   Text to encode in the QR code.
     * @param width  Width of the QR code in pixels.
     * @param height Height of the QR code in pixels.
     * @return A PNG image of the QR code as a byte array.
     * @throws WriterException If a QR code could not be generated.
     * @throws IOException     If there was an error writing to the output stream.
     */
    public static byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig config = new MatrixToImageConfig(0xFF000002, 0xFFFFFFFF);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, config);

        return pngOutputStream.toByteArray();
    }

    public static String getQRCode(int width, int height)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // TODO: get user id from session
        // temp data until login system is implemented
        long tempUserID = 1L;

        HashMap<String, String> payload = new HashMap<>();
        payload.put("userId", String.valueOf(tempUserID));
        payload.put("timestamp", Instant.now().toString());

        String secretKey = System.getenv("QR_ENCRYPTION_KEY");
        if (secretKey == null) {
            throw new IllegalStateException("Secret key not found");
        }
        payload.put("secretKey", secretKey);

        String encryptedPayload = QREncryptionUtils.encrypt(String.valueOf(payload));

        byte[] image;
        try {
            // Generate QR code as byte array
            image = generateQRCode(encryptedPayload, width, height);

        } catch (WriterException e) {
            return "failed to generate QR code: " + e.getMessage();
        } catch (IOException e) {
            return "Unexpected error in generating QR code: " + e.getMessage();
        }

        // Convert byte array into base64 encode String
        return Base64.getEncoder().encodeToString(image);
    }
}
