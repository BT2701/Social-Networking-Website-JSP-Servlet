package org.example.j2ee.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.NotificationSV;
import org.example.j2ee.Service.PostSV;
import org.example.j2ee.Service.UserSV;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.module.Configuration;

import org.example.j2ee.Controller.PostCTL;

@WebServlet("/api/post/*")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 10485760)
public class PostApi extends HttpServlet {

    PostCTL postCTL = new PostCTL();
    public final ObjectMapper mapper = new ObjectMapper();
    private final NotificationSV notificationSV = new NotificationSV();
    private final PostSV postSV = new PostSV();
    private final UserSV userSV = new UserSV();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/like":
                    handleLike(req, resp);
                    break;
                case "/unLike":
                    handleUnLike(req, resp);
                    break;
                case "/comment":
                    handleComment(req, resp);
                    break;
                case "/create":
                    // System.out.println("Handling /create"); // Log for debugging
                    handleCreatePost(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown request path: " + path);
                    break;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Path is required");
        }

    }

    private void handleLike(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int postId = Integer.parseInt(req.getParameter("postId"));

        boolean success = postSV.likePost(userId, postId);

        // Gửi thông báo cho chủ bài post
        Post post = postSV.getPostById(postId);
        int receiverId = post.getUser().getId();
        if(receiverId != userId) {
            User user = userSV.findUserById(userId);
            String content = user.getName() + " liked your post !";
            notificationSV.createOrUpdateNotification(content, user, receiverId, post);
        }

        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Like successful\"}");
        } else {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to like post");
        }
    }

    private void handleUnLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int postId = Integer.parseInt(req.getParameter("postId"));

        boolean success = postSV.unLikePost(userId, postId);

        Post post = postSV.getPostById(postId);
        int receiverId = post.getUser().getId();
        if(receiverId != userId) {
            User user = userSV.findUserById(userId);
            String content = user.getName() + " unliked your post !";
            notificationSV.deleteLikeNotification(content, user, receiverId, postId);
        }

        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"UnLike successful\"}");
        } else {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to unlike post");
        }
    }

    private void handleComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int postId = Integer.parseInt(req.getParameter("postId"));
        String content = req.getParameter("commentContent");

        Comment cmt = postSV.commentPost(userId, postId, content);

        // Gửi thông báo cho chủ bài post
        Post post = postSV.getPostById(postId);
        int receiverId = post.getUser().getId();
        if(receiverId != userId) {
            User user = userSV.findUserById(userId);
            String ntfContent = user.getName() + " commented on your post !";
            notificationSV.createNotification(ntfContent, user, receiverId, post);
        }

        if (cmt != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), cmt);
        } else {
            sendJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to comment on post");
        }
    }

    private void sendJsonError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"message\": \"" + message + "\"}");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        Part filePart = req.getPart("postImage");
        String fileName = "";
        if (filePart != null && filePart.getSize() > 0) {
            fileName = UploadAvatarApi.uploadImage(req, resp, "postImage");
        }

        int postId = Integer.parseInt(req.getParameter("postId"));
        String content = req.getParameter("postContent");

        Post post = postSV.getPostById(postId);

        if (post != null) {
            post.setContent(content);

            if (!fileName.isEmpty()) {
                post.setImage(fileName);
            }

            Post postUpdate = postSV.updatePost(post);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Upload successful\", \"fileName\": \"" + postUpdate.getImage() + "\", \"content\": \"" + postUpdate.getContent() + "\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"message\": \"Post not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));

        try {
            Post post = postSV.getPostById(postId);

            if(post != null) {
                String fileName = post.getImage();
                String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
                File file = new File(uploadDir + File.separator + fileName);
                if (file.exists()) {
                    file.delete();
                }

                if(postSV.deletePost(postId)) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleCreatePost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Get the current session without creating a new one
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            String content = req.getParameter("statusText");

            // Retrieve the file part from the request (with the name "image" in FormData)
            Part filePart = req.getPart("image");
            String fileName = filePart.getSubmittedFileName(); // Get the original file name

            // Define the name of the upload directory
            final String UPLOAD_DIRECTORY = "uploads";
            // Get the absolute path of the web application
            String appPath = req.getServletContext().getRealPath("");
            // Construct the full path of the upload directory
            String uploadPath = appPath + File.separator + UPLOAD_DIRECTORY;
            // Create a File object for the upload directory
            File folder = new File(uploadPath);
            // Check if the folder does not exist or is not a directory, create it
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdirs(); // Creates the directory if it does not exist
            }

            // Save the uploaded file into this directory
            filePart.write(uploadPath + File.separator + fileName); // Write the file to the server

            // Create the post
            Post post = postCTL.createPost(userId, content, fileName);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            if (post != null) {
                // Convert the post object to JSON and send it back
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(resp.getOutputStream(), post);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Failed to create post\"}");
            }
        } else {
            // Send error response if the user is not logged in
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"You must login\"}");
        }
    }


//    private void handleCreatePost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        HttpSession session = req.getSession(false); // Get the current session without creating a new one
//        if (session != null) {
//            Integer userId = (Integer) session.getAttribute("userId");
//            String content = req.getParameter("statusText");
//
//            // Retrieve the file part from the request (with the name "image" in FormData)
//            Part filePart = req.getPart("image");
//            String fileName = filePart.getSubmittedFileName(); // Get the original file name
//            //LƯU ẢNH VÀO FOLDER "uploads"
//            // Define the name of the upload directory
//            final String UPLOAD_DIRECTORY = "uploads";
//            // Get the absolute path of the web application
//            String appPath = req.getServletContext().getRealPath("");
//            // Construct the full path of the upload directory
//            String uploadPath = appPath + File.separator + UPLOAD_DIRECTORY;
//            // Create a File object for the upload directory
//            File folder = new File(uploadPath);
//            // Check if the folder does not exist or is not a directory, create it
//            if (!folder.exists() || !folder.isDirectory()) {
//                folder.mkdirs(); // Creates the directory if it does not exist
//            }
//            // Now, save the uploaded file into this directory
//            try (PrintWriter out = resp.getWriter()) {
//                // Save the uploaded file to the upload directory
//                filePart.write(uploadPath + File.separator + fileName); // Write the file to the server
//                Post post = postCTL.createPost(userId, content, fileName);
//                if (post != null) {
////                    resp.getWriter().print("Create post success hehe!" + " post id: " + post.getId() + "post content: " + post.getContent() + "post image: " + post.getImage() + "likeByUser:" + post.isLikedByUser() + "numberComment: " + post.getNumComments() + "number like: " + post.getNumReactions() + "user create: " + post.getUser().getId() + "user name: " + post.getUser().getName() + "user iamge: " + post.getUser().getAvt()
////                    );
////                    resp.getWriter().print("Create post sucess!");
//                    mapper.writeValue(resp.getOutputStream(), post);
//                } else {
//                    resp.getWriter().print("Fail to create post!");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                resp.getWriter().print("Error uploading file: " + e.getMessage());
//            }
//        } else {
//            //DẪN ĐẾN TRANG ĐĂNG NHẬP
//            //(Còn lỗi nếu session chưa có thì chưa gửi cái này về được)
//            resp.getWriter().print("You must login");
//        }
//    }
}
