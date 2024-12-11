let countdownTime = 300; // countdown time in seconds

function updateTimerAndQR() {
    if (countdownTime <= 0) {
        countdownTime = 300;
        // Refresh the page to regenerate the QR code
        location.reload();
    } else {
        let minutes = Math.floor(countdownTime / 60);
        let seconds = countdownTime % 60;

        document.getElementById('timer').innerText = `${minutes}:${seconds < 10 ? '0' : ''}${seconds} remaining`;
        countdownTime--;
    }
}

setInterval(updateTimerAndQR, 1000);