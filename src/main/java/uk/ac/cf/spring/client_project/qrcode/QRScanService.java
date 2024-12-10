package uk.ac.cf.spring.client_project.qrcode;

import org.springframework.http.ResponseEntity;


public interface QRScanService {
    /**
     * Scans the QR code and returns the approval status
     *
     * @param qrData The QR code data as a string
     * @return The approval status of a visitor
     */
    ResponseEntity<String> scanQRCode(String qrData, Long locationId);
}
