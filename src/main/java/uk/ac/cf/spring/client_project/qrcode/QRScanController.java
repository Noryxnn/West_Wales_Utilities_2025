package uk.ac.cf.spring.client_project.qrcode;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QRScanController {
    QRScanService qrScanService;

    @Autowired
    public QRScanController(QRScanService qrScanService) {
        this.qrScanService = qrScanService;
    }

    @PostMapping("/scan")
    public ResponseEntity<String> handleQRCodeScan(@RequestBody String qrData, HttpSession session) {
        Long locationId = (Long) session.getAttribute("locationId");
        return qrScanService.scanQRCode(qrData, locationId);
    }
}
