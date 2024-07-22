package org.example.j2ee.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.ProfileSV;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/profile/*")
public class ProfileApi extends HttpServlet {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private ProfileSV profileService;

    @Override
    public void init() throws ServletException {
        profileService = new ProfileSV();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int userId = Integer.parseInt(pathParts[1]);
            User existingUser = profileService.getUserById(userId);
            if (existingUser == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            User user = objectMapper.readValue(request.getInputStream(), User.class);
            user.setId(userId);

            existingUser.setName(user.getName());
            existingUser.setBirth(user.getBirth());
            existingUser.setAddress(user.getAddress());
            existingUser.setDesc(user.getDesc());
            existingUser.setEducation(user.getEducation());
            existingUser.setEmail(user.getEmail());
            existingUser.setGender(user.getGender());
            existingUser.setPhone(user.getPhone());
            existingUser.setRelationship(user.getRelationship());
            existingUser.setSocial(user.getSocial());

            User updatedUser = profileService.updateUser(existingUser);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(), updatedUser);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi chi tiết
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
