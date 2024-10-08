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

        const dropdown = document.querySelector(".notification-box");
        if(!dropdown.classList.contains("d-block")) {
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
        }
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
        let notification = JSON.parse(event.data);

        // Xóa tất cả các thông báo hiện tại trong notification-container
        const notificationContainer = document.getElementById("notification-container");
        notificationContainer.innerHTML = "";

        // Gọi API để lấy danh sách thông báo mới
        fetch('/api/notification?userId=' + curUserId)
            .then(response => response.json())
            .then(notifications => {
                notifications.forEach(notification => {
                    const ntf = document.createElement('div');
                    ntf.classList.add('notification-item', 'd-flex');
                    if (notification.is_read != 1) {
                        ntf.classList.add('notification-unread');
                    }

                    ntf.innerHTML = `
                    <div class="notification__image-container">
                        <img src="/uploads/${notification.user.avt}" alt="${notification.user.name}">
                    </div>
                    <div class="notification__infor">
                        <div class="notification-content truncate-2line">
                            ${notification.content}
                        </div>
                    </div>
                `;

                    notificationContainer.appendChild(ntf);
                });

                // Cập nhật số lượng thông báo chưa đọc
                const ntfQuantity = document.querySelector(".notification-quantity");
                const unreadCount = notifications.filter(n => n.is_read != 1).length;
                ntfQuantity.innerHTML = unreadCount > 99 ? "99+" : unreadCount.toString();

                // Hiển thị nội dung thông báo mới nhất (nếu cần)
                alert(notification.content);
            })
            .catch(error => console.error('Error fetching notifications:', error));
    };
    socket.onclose = function(event) {
        alert("Close notificationSocket !");
    };
}
