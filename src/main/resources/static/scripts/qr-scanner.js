// QR code scanner script for scanning visitor QR codes
// Used to check in visitors and record their on-site attendance
// https://scanapp.org/html5-qrcode-docs/docs/apis/classes/Html5Qrcode
async function startQrScanner() {
    const html5QrCode = new Html5Qrcode("qr-reader");
    const qrReaderResults = document.getElementById("qr-reader-results");
    qrReaderResults.innerHTML = "";

    let isProcessing = false;

    function onScanSuccess(decodedText) {
        if (isProcessing) {
            return;
        }
        isProcessing = true;

        console.log(`Code scanned: ${decodedText}`);

        // Send encrypted data to the server
        fetch('/scan', {
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
                if (data.includes('approved')) {
                    qrReaderResults.innerHTML = `<p style="color: green;">Visitor is approved</p>`;

                } else if (data.includes('denied')) {
                    qrReaderResults.innerHTML = `<p style="color: red;">Visitor is denied</p>`;

                } else {
                    qrReaderResults.innerHTML = `<p>Invalid QR code</p>`;
                }
                qrReaderResults.innerHTML += `<button class="btn-primary" id="scan-again-btn" onclick="startQrScanner()">Scan Again</button>`;


            })
            .catch(error => {
                console.error('Error sending data:', error);
                qrReaderResults.innerHTML = `<p style="color: red;">Error sending data: ${error}</p>`;
            })
            .finally(() => {
                html5QrCode.stop();
                isProcessing = false;
            });
    }

    function onScanFailure(error) {
        console.warn(`QR scan failed: ${error}`);
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