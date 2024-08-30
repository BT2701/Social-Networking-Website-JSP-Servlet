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
        request.getRequestDispatcher("Template/Home/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
