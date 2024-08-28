document.addEventListener("DOMContentLoaded", function() {
    const iconNotification = document.getElementById("icon-notification");
    const notificationQuantityElement = document.querySelector(".notification-quantity");

    iconNotification.addEventListener("click", function() {
        const userId = iconNotification.getAttribute("data-user-id");

        if(userId == -1) {
            alert("Please login before performing action!");
            return;
        }

        // Gửi yêu cầu PUT đến API
        fetch("/api/notification?userId=" + userId, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("Failed to mark notifications as read");
                }
            })
            .then(data => {
                if (data.success) {
                    notificationQuantityElement.textContent = "0";

                    const unreadNotifications = document.querySelectorAll(".notification-unread");
                    unreadNotifications.forEach(notification => {
                        notification.classList.remove("notification-unread");
                    });
                } else {
                    console.error("Failed to mark notifications as read");
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
});