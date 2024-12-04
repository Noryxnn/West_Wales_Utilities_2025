// QR code scanner script for scanning visitor QR codes
// Used to check in visitors and record their on-site attendance
// https://scanapp.org/html5-qrcode-docs/docs/apis/classes/Html5Qrcode
async function startQrScanner() {
    const qrReaderResults = document.getElementById("qr-reader-results");

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
            .then(data => console.log('Server Response:', data))
            .catch(error => console.error('Error sending data:', error));
    }

    function onScanFailure(error) {
        console.warn(`QR scan failed: ${error}`);
    }

    try {
        // Request camera permissions
        const stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } });
        stream.getTracks().forEach(track => track.stop()); // Stop after permission check

        // Start the QR scanner
        const html5QrCode = new Html5Qrcode("qr-reader");
        await html5QrCode.start(
            { facingMode: "environment" },
            { fps: 10, qrbox: 250 }, // Scanner settings
            onScanSuccess,
            onScanFailure
        );
    } catch (error) {
        console.error("Camera access denied or unavailable:", error);
        qrReaderResults.innerHTML = `<p>Error: Camera access denied or unavailable.</p>`;
    }
}
document.addEventListener("DOMContentLoaded", startQrScanner);