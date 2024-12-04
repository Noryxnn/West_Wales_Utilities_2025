package uk.ac.cf.spring.client_project.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.cf.spring.client_project.security.QREncryptionUtils;

import java.util.HashMap;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    StaffService staffService;

    @GetMapping("/dashboard")
    public String getStaffDashboard() {
        return "staff/staff-dashboard";
    }

    @PostMapping("/scan")
    public ResponseEntity<String> handleQRCodeScan(@RequestBody String qrData) {
        String decryptedData;
        try {
            // Decrypt the QR code data
            decryptedData = QREncryptionUtils.decrypt(qrData);

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

        HashMap<String, Object> decryptedDataMap = staffService.stringToHashMap(decryptedData);
        System.out.println(decryptedDataMap);

        return ResponseEntity.ok("QR code processed successfully!");
    }
}
