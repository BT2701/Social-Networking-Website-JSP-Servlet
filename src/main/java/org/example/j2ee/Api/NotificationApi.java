package org.example.j2ee.Api;

import jakarta.servlet.ServletException;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/add":
                    handleAddNotification(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown request path: " + path);
                    break;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Path is required");
        }

    }

    public void handleAddNotification (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String content = req.getParameter("content");
        int userId = Integer.parseInt(req.getParameter("userId"));

        boolean success = notificationSV.createNotification(content, userId);

        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"success\": true}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false}");
        }
    }

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
