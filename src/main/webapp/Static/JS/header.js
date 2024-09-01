//XỬ LÝ HIỂN THỊ BOX NOTIFICATION,MESSENGER AND USER OPERATION
document.addEventListener("DOMContentLoaded", () => {
    function setupDropdown(iconId, dropdownClass) {
        const icon = document.getElementById(iconId);
        const dropdown = document.querySelector(dropdownClass);

        // Toggle the dropdown visibility
        icon.addEventListener("click", (event) => {
            //   event.stopPropagation(); // Prevent the click event from propagating to the document
            dropdown.classList.toggle("d-block");
        });
        // Hide dropdown when clicking outside
        document.addEventListener("click", (event) => {
            if (!icon.contains(event.target) && !dropdown.contains(event.target)) {
                dropdown.classList.remove("d-block");
            }
        });
    }
    // Setup each icon with its corresponding dropdown
    setupDropdown("icon-messenger", ".messenger-box");
    setupDropdown("icon-notification", ".notification-box");
    setupDropdown("icon-user-operation", ".user-operation-box");


    // hien thi notifications
    const iconNotification = document.getElementById("icon-notification");
    const notificationQuantityElement = document.querySelector(".notification-quantity");

    iconNotification.addEventListener("click", function() {
        const userId = iconNotification.getAttribute("data-user-id");

        if(userId == -1) {
            // alert("Please login before performing action!");
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

const curUserId = document.getElementById("icon-notification").getAttribute("data-user-id");
if(curUserId && curUserId != -1) {
    let socket = new WebSocket("ws://localhost:8080/notifications/" + curUserId);
    console.log("ws://localhost:8080/notifications/" + curUserId);

    socket.onopen = function(event) {
        alert("Connected to notificationSocket successfully !");
    };
    socket.onmessage = function(event) {
        let notification = event.data;

        const notificationContainer = document.getElementById("notification-container");
        const ntf = document.createElement('div');
        ntf.classList.add('notification-item');
        ntf.classList.add('d-flex');
        ntf.classList.add('notification-unread');
        ntf.innerHTML = `
                        <div class="notification__image-container">
                            <img src="/uploads/img1.jpg" alt="Contact 1">
                        </div>
                        <div class="notification__infor">
                            <div class="notification-content truncate-2line">
                                    ${notification}
                            </div>
                        </div>
                    `;

        const ntfQuantity = document.querySelector(".notification-quantity");
        const numOfNtf = parseInt(ntfQuantity.textContent);
        ntfQuantity.innerHTML = (numOfNtf + 1).toString();
        notificationContainer.prepend(ntf);

        alert(notification);
    };
    socket.onclose = function(event) {
        alert("Close notificationSocket !");
    };
}
