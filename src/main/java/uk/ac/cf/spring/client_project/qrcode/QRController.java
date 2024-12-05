package uk.ac.cf.spring.client_project.qrcode;

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
    @Autowired
    StaffService staffService;
    @PostMapping("/scan")
    public ResponseEntity<String> handleQRCodeScan(@RequestBody String qrData) {
        Map<String, Object> decryptedData;
        try {
            // Decrypt the QR code data
            decryptedData = staffService.stringToHashMap(QREncryptionUtils.decrypt(qrData));

            // Validate that the decrypted data contains required fields
            if (!QREncryptionUtils.validateDecryptedData(decryptedData)) {
                System.out.println("Invalid QR code data");
                return ResponseEntity.badRequest().body("Invalid QR code data: Missing required fields");

            }
        } catch (Exception e) {
            System.out.println("Failed to decrypt QR code data: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to decrypt QR code data: " + e.getMessage());
        }

        System.out.println("QR Code Data Received: " + qrData);
        System.out.println("Decrypted Data: " + decryptedData);

        Long userId = Long.parseLong(decryptedData.get("userId").toString());
        if (staffService.isVisitorApproved(userId)) {
            return ResponseEntity.ok("approved");
        } else {
            return ResponseEntity.ok("denied");
        }
    }
}
