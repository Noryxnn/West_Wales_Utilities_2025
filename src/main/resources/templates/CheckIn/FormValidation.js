document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("checkinForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent the form from submitting

        // Collect form fields
        const fields = [
            { id: "userID", name: "User ID" },
            { id: "firstname", name: "First Name" },
            { id: "lastname", name: "Last Name" },
            { id: "email", name: "Email Address" },
            { id: "companyName", name: "Company Name" },
        ];

        let isValid = true;

        // Check each field for validity
        fields.forEach((field) => {
            const input = document.getElementById(field.id);
            const value = input.value.trim();

            if (!value) {
                isValid = false;
                input.style.borderColor = "red"; // Highlight the field in red
            } else {
                input.style.borderColor = ""; // Reset to default
            }
        });

        // If any field is invalid, alert the user and stop submission
        if (!isValid) {
            alert("Please fill in all required fields.");
            return false;
        }

        // Success message
        alert("Check-in successful. Welcome!");
        return true;
    });
});

