package org.example.j2ee;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;

@WebServlet(name = "homepage", value = "/homepage")
public class HelloServlet extends HttpServlet {
    private String message;

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
