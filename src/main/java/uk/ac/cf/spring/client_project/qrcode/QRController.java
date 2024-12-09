package uk.ac.cf.spring.client_project.qrcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cf.spring.client_project.security.QREncryptionUtils;
import uk.ac.cf.spring.client_project.staff.StaffService;

import java.util.Map;

@RestController
public class QRController {
    private static final Logger logger = LoggerFactory.getLogger(QRController.class);
    StaffService staffService;

    @Autowired
    public QRController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/scan")
    public ResponseEntity<String> handleQRCodeScan(@RequestBody String qrData) {
        Map<String, Object> decryptedData;
        try {
            // Decrypt the QR code data
            decryptedData = staffService.stringToHashMap(QREncryptionUtils.decrypt(qrData));

            // Validate that the decrypted data contains required fields
            if (!QREncryptionUtils.validateDecryptedData(decryptedData)) {
                return ResponseEntity.badRequest().body("Invalid QR code data: Missing required fields");

            }
        } catch (Exception e) {
            logger.error("Failed to decrypt QR code data: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Failed to decrypt QR code data: " + e.getMessage());
        }

        Long userId = Long.parseLong(decryptedData.get("userId").toString());
        if (staffService.isVisitorApproved(userId)) {
            return ResponseEntity.ok("approved");
        } else {
            return ResponseEntity.ok("denied");
        }
    }
}
