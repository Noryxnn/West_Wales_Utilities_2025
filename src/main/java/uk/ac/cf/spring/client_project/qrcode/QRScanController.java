package uk.ac.cf.spring.client_project.qrcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<String> handleQRCodeScan(@RequestBody String qrData) {
        return qrScanService.scanQRCode(qrData);
    }
}
