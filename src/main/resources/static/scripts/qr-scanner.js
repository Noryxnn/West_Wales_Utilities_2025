// QR code scanner script for scanning visitor QR codes
// Used to check in visitors and record their on-site attendance
// https://scanapp.org/html5-qrcode-docs/docs/apis/classes/Html5Qrcode
async function startQrScanner() {
    const html5QrCode = new Html5Qrcode("qr-reader");

    const qrReaderResults = document.getElementById("qr-reader-results");
    qrReaderResults.innerHTML = "";

    function onScanSuccess(decodedText) {
        console.log(`Code scanned: ${decodedText}`);

        // Send encrypted data to the server
        fetch('/staff/scan', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            },
            body: decodedText
        })
            .then(response => response.text())
            .then(data => {

                // Process server response
                console.log('Server Response:', data);
                if (data.includes('QR code processed successfully!')) {
                    qrReaderResults.innerHTML = `<p style="color: green;">${data}</p>`;
                } else {
                    qrReaderResults.innerHTML = `<p>Invalid QR code</p>`;
                }
                html5QrCode.stop();

            })
            .catch(error => {
                console.error('Error sending data:', error);
                qrReaderResults.innerHTML = `<p style="color: red;">Error sending data: ${error}</p>`;
            });
    }

    function onScanFailure(error) {
        console.warn(`QR scan failed: ${error}`);
        html5QrCode.stop();
    }

    try {
        // Request camera permissions
        await navigator.mediaDevices.getUserMedia({video: {facingMode: "environment"}});

        // Start the QR scanner
        await html5QrCode.start(
            {facingMode: "environment"},
            {fps: 10, qrbox: 250}, // Scanner settings
            onScanSuccess,
            onScanFailure
        );
    } catch (error) {
        console.error("Camera access denied or unavailable:", error);
        qrReaderResults.innerHTML = `<p>Error: Camera access denied or unavailable.</p>`;
    }
}

document.addEventListener("DOMContentLoaded", startQrScanner);