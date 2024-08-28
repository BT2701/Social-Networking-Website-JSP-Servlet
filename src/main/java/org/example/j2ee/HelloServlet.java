package org.example.j2ee;

import java.io.*;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;
import org.example.j2ee.Model.Notification;
import org.example.j2ee.Service.NotificationSV;

@WebServlet(name = "homepage", value = {"/homepage", ""})
public class HelloServlet extends HttpServlet {
    private String message;
    private static NotificationSV notificationSV = new NotificationSV();

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        if (currentUserId == null) {
            currentUserId = -1;
        }

        if(currentUserId != -1) {
            List<Notification> notifications = notificationSV.getAllNotificationsByUserId(String.valueOf(currentUserId));
            long countUnreadNotifications = notifications.stream().filter(notification -> notification.getIs_read() != 1).count();
            String displayCount = countUnreadNotifications > 99 ? "99+" : String.valueOf(countUnreadNotifications);

            request.setAttribute("userId", currentUserId);
            request.setAttribute("notificationsQuantity", displayCount);
            request.setAttribute("notifications", notifications);
        } else {
            request.setAttribute("userId", -1);
            request.setAttribute("notificationsQuantity", "0");
            request.setAttribute("notifications", Collections.emptyList());
        }

        request.getRequestDispatcher("Template/Home/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
