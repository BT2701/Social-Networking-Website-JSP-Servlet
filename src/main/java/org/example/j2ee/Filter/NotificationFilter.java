package org.example.j2ee.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.j2ee.Model.Notification;
import org.example.j2ee.Service.NotificationSV;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class NotificationFilter implements Filter {

    private NotificationSV notificationService = new NotificationSV();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId.toString());
            long countUnreadNotifications = notifications.stream().filter(notification -> notification.getIs_read() != 1).count();
            String displayCount = countUnreadNotifications > 99 ? "99+" : String.valueOf(countUnreadNotifications);

            request.setAttribute("notifications", notifications);
            request.setAttribute("notificationsQuantity", displayCount);
        } else {
            request.setAttribute("notificationsQuantity", "0");
            request.setAttribute("notifications", Collections.emptyList());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Hủy nếu cần
    }
}
