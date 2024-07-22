package org.example.j2ee.Api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.ProfileSV;

@WebServlet("/api/uploadAvatar")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 10485760)
public class UploadAvatarApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getPathInfo();
        if (fileName == null || fileName.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String savePath = getServletContext().getRealPath("") + File.separator + "uploads";
        File file = new File(savePath + fileName);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream())
        {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json");

        String fileName = UploadAvatarApi.uploadImage(request, response, "avatarImage");

        int userId = Integer.parseInt(request.getParameter("userId"));
        ProfileSV userDAO = new ProfileSV();
        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setAvt(fileName);
            userDAO.updateUser(user);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\": \"Upload successful\", \"fileName\": \"" + fileName + "\"}");
    }

    public static String uploadImage (HttpServletRequest request, HttpServletResponse response, String filePartName
    ) throws ServletException, IOException {
        Part filePart = request.getPart(filePartName);

        if (filePart == null || filePart.getSize() > 5242880) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"File size exceeds limit or no file selected\"}");
            return "";
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "-" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String savePath = request.getServletContext().getRealPath("") + File.separator + "uploads";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        filePart.write(savePath + File.separator + fileName);

        return fileName;
    }
}
