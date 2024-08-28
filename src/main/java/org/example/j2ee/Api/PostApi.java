package org.example.j2ee.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Service.PostSV;

import java.io.File;
import java.io.IOException;

@WebServlet("/api/post/*")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 10485760)
public class PostApi extends HttpServlet {
    PostSV postSV = new PostSV();
    public final ObjectMapper mapper = new ObjectMapper();

//    private NotificationSV notificationService = new NotificationSV();

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
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown request path: " + path);
                    break;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Path is required");
        }

    }

    private void handleLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int postId = Integer.parseInt(req.getParameter("postId"));

        boolean success = postSV.likePost(userId, postId);

        // Gửi thông báo cho chủ bài post
        Post post = postSV.getPostById(postId);
//        notificationService.notify(String.valueOf(post.getUser().getId()), userId + " đã thả tim bài viết của bạn.");

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
//        notificationService.notify(String.valueOf(post.getUser().getId()), userId + " đã bình luận: " + cmt.getContent());

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
}
