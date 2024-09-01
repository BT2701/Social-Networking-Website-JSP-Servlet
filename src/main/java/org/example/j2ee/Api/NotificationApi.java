package org.example.j2ee.Api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.j2ee.Service.NotificationSV;

import java.io.IOException;

@WebServlet("/api/notification/*")
public class NotificationApi extends HttpServlet {
    private final NotificationSV notificationSV = new NotificationSV();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Lấy userId từ query string
        String userId = req.getParameter("userId");

        if (userId == null || userId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid userId");
            return;
        }

        boolean isSuccess = notificationSV.markAllNotificationsAsRead(userId);

        if (isSuccess) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"success\": true}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false}");
        }
    }
}
