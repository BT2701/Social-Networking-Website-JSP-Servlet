package org.example.j2ee.Controller;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.ProfileSV;

@WebServlet(name = "profile", value = "/profile")
public class ProfileCTL extends HttpServlet  {
    private final ProfileSV profileService = new ProfileSV();

//    private static final Logger LOGGER = Logger.getLogger(ProfileCTL.class.getName());
//    LOGGER.info("Friends count for user " + userId + ": " + friendsCount);

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userIdParam = request.getParameter("userId");
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");

        if (currentUserId == null) {
            response.sendRedirect("login");
            return;
        }

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            User user = profileService.getUserById(userId);

            if (user == null) {
                response.sendRedirect("index.jsp");
            } else {
                long friendsCount = profileService.countFriends(userId);
                List<User> friends = profileService.getFriendsFromUserId(userId);
                List<Post> posts = profileService.getPostsFromUserId(userId, currentUserId);

                request.setAttribute("posts", posts);
                request.setAttribute("user", user);
                request.setAttribute("friends", friends);
                request.setAttribute("friendsCount", friendsCount);
                request.getRequestDispatcher("/Template/Profile/profile.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
