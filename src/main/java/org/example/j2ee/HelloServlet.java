package org.example.j2ee;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.List;

import lombok.SneakyThrows;
import org.example.j2ee.Controller.PostCTL;
import org.example.j2ee.Controller.ProfileCTL;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.ProfileSV;

@WebServlet(name = "homepage", value = "/homepage")
public class HelloServlet extends HttpServlet {

    private String message;
    private PostCTL postCTL = new PostCTL();
    private ProfileSV profileSV = new ProfileSV();

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User existingUser = null;
        Integer userId = null;
        //THÔNG TIN USER ĐANG SỬ DỤNG DỊCH VỤ
        HttpSession session = request.getSession(false); // Get the current session without creati
        if (session != null) {
            userId = (Integer) session.getAttribute("userId");
        }
        existingUser = profileSV.getUserById(1);
        //THÔNG TIN CÁC BÀI POST MOI NHAT
        List<Post> posts = postCTL.getTheNewestPosts();
        //THÔNG TIN DANH SÁCH BẠN CỦA USER ĐANG SỬ DỤNG DỊCH VỤ
        //THÔNG TIN DANH SÁCH GROUP CHAT CỦA USER ĐANG SỬ DỤNG DỊCH VỤ

        // SET DATA AS REQUEST ATTRIBUTE
        request.setAttribute("posts", posts);
        request.setAttribute("existingUser", existingUser);
        request.getRequestDispatcher("Template/Home/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
