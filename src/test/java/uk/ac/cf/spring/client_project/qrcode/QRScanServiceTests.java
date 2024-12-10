package uk.ac.cf.spring.client_project.qrcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QRScanServiceTests {
    @Autowired
    QRScanServiceImpl qrScanServiceImpl;

    @Test
    void shouldConvertQRDataStringToHashMap() {
        String input = "{secretKey=123=, userId=1, timestamp=2024-12-04T19:31:02.653446300Z}";

        HashMap<String, Object> output = qrScanServiceImpl.qrDataToHashmap(input);

        assertEquals(3, output.size());
        assertEquals("1", output.get("userId"));
        assertEquals("2024-12-04T19:31:02.653446300Z", output.get("timestamp"));
        assertEquals("123=", output.get("secretKey"));
    }
}
