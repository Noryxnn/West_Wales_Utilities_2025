package uk.ac.cf.spring.client_project.qrcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.security.QREncryptionUtils;
import uk.ac.cf.spring.client_project.staff.StaffService;
import uk.ac.cf.spring.client_project.visit.VisitDTO;
import uk.ac.cf.spring.client_project.visit.VisitService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRScanServiceImpl implements QRScanService {
    private static final Logger logger = LoggerFactory.getLogger(QRScanServiceImpl.class);
    StaffService staffService;
    VisitService visitService;

    public QRScanServiceImpl(StaffService staffService, VisitService visitService) {
        this.staffService = staffService;
        this.visitService = visitService;
    }

    public ResponseEntity<String> scanQRCode(String qrData, Long locationId) {
        Map<String, Object> decryptedData;
        try {
            // Decrypt the QR code data
            decryptedData = qrDataToHashmap(QREncryptionUtils.decrypt(qrData));

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
            // Check-in/out visitor and return the appropriate response
            return recordAttendanceIfApproved(userId, locationId);
        } else {
            return ResponseEntity.ok("denied");
        }
    }

    private ResponseEntity<String> recordAttendanceIfApproved(Long userId, Long locationId) {
        VisitDTO visit = visitService.getCurrentVisit(userId, locationId);

        if (visit != null && visit.getCheckOutDateTime() == null) {
            // Update the visit to mark it as checked out
            checkOut(visit, userId, locationId);

            return ResponseEntity.ok("checkout");
        } else {
            checkIn(userId, locationId);
            return ResponseEntity.ok("checkin");
        }
    }

    private void checkIn(Long userId, Long locationId) {
        VisitDTO visit = new VisitDTO();
        visit.setUserId(userId);
        visit.setLocationId(locationId);
        visit.setCheckInDateTime(LocalDateTime.now());

        visitService.save(visit);

        logger.info("Visitor {} checked in at location {}", userId, locationId);
    }

    private void checkOut(VisitDTO visit, Long userId, Long locationId) {
        visit.setCheckOutDateTime(LocalDateTime.now());
        visitService.update(visit);

        logger.info("Visitor {} checked out at location {}", userId, locationId);
    }

    /**
     * Converts QR code data to a HashMap
     *
     * @param input The QR code data
     * @return Data as a HashMap
     */
    HashMap<String, Object> qrDataToHashmap(String input) {
        input = input.substring(1, input.length() - 1);

        HashMap<String, Object> map = new HashMap<>();
        String[] splitInput = input.split(", ");

        for (String s : splitInput) {
            String[] splitItem = s.split("=", 2);
            String key = splitItem[0].trim();
            String value = splitItem[1].trim();

            map.put(key, value);
        }
        return map;
    }
}
