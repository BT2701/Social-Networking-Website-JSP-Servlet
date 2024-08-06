package org.example.j2ee.Api;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.example.j2ee.Controller.FriendRequestCTL;

@WebServlet(name = "FriendRequestApi", value = "/friendRequest")
public class FriendRequestApi extends HttpServlet {

    private FriendRequestCTL friendRequestCTL;

    @Override
    public void init() throws ServletException {
        super.init();
        this.friendRequestCTL = new FriendRequestCTL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String limit;

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        // Nếu không có tham số action, chuyển hướng tới trang JSP
        if (action == null) {
            request.getRequestDispatcher("/Template/Friend_Request/friend_request.jsp").forward(request, response);
            return;
        }
        try {
            switch (action) {
                case "getAllFriendRequestsByReceiverId":
                    System.out.println("user id ReceiverId:" + userId);
                    limit = request.getParameter("limit") + "";
                    if (userId != null) {
                        List<Map<String, Object>> friendRequests = friendRequestCTL.getAllFriendRequestsByReceiverId(userId, limit);
                        Gson gson = new Gson();
                        String jsonResponse = gson.toJson(friendRequests);
                        out.print(jsonResponse);
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"User not logged in\"}");
                        out.flush();
                    }
                    break;
                case "getAllFriendRequestsBySenderId":
                    System.out.println("user id SenderId:" + userId);
                    limit = request.getParameter("limit") + "";
                    System.out.println("limit sender" + limit);
                    if (userId != null) {
                        List<Map<String, Object>> friendRequests = friendRequestCTL.getAllFriendRequestsBySenderId(userId, limit);
                        Gson gson = new Gson();
                        String jsonResponse = gson.toJson(friendRequests);
                        out.print(jsonResponse);
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"User not logged in\"}");
                        out.flush();
                    }
                    break;
                case "loadPageFriendRequest":
                    // Chuyển hướng đến trang friend_request.jsp
                    request.getRequestDispatcher("/Template/Friend_Request/friend_request.jsp").forward(request, response);
                    break;
                case "confirmRequest":
                    String requestIdStr = request.getParameter("requestid");
                    if (userId != null && requestIdStr != null) {
                        try {
                            int requestId = Integer.parseInt(requestIdStr);
                            boolean success = friendRequestCTL.confirmFriendRequest(requestId);
                            System.out.println("ket qua" + success);
                            if (success) {
                                out.print("{\"success\": true, \"message\": \"Friend request confirmed successfully\"}");
                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("{\"success\": false, \"message\": \"Failed to confirm friend request\"}");
                            }
                        } catch (NumberFormatException e) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"success\": false, \"message\": \"Invalid request ID\"}");
                        }
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"Invalid request parameters\"}");
                        out.flush();
                    }
                    break;
                case "deleteRequest":
                    String deleteRequestIdStr = request.getParameter("requestid");
                    if (userId != null && deleteRequestIdStr != null) {
                        try {
                            int requestId = Integer.parseInt(deleteRequestIdStr);
                            boolean success = friendRequestCTL.deleteRequest(requestId);
                            System.out.println("ket qua" + success);
                            if (success) {
                                out.print("{\"success\": true, \"message\": \"Friend request deleted successfully\"}");
                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("{\"success\": false, \"message\": \"Failed to delete friend request\"}");
                            }
                        } catch (NumberFormatException e) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"success\": false, \"message\": \"Invalid request ID\"}");
                        }
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"Invalid request parameters\"}");
                        out.flush();
                    }
                    break;
                case "getListSuggestedFriends":
                    System.out.println("user id SuggestedFriends:" + userId);
                    limit = request.getParameter("limit") + "";
                    System.out.println("limit sender" + limit);
                    if (userId != null) {
                        List<Map<String, Object>> suggestedFriends = friendRequestCTL.getListSuggestedFriends(userId, limit);
                        Gson gson = new Gson();
                        String jsonResponse = gson.toJson(suggestedFriends);
                        out.print(jsonResponse);
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"User not logged in\"}");
                        out.flush();
                    }
                    break;
                case "Addfriend":
                    String addUserIdStr = request.getParameter("userIdToAdd");
                    if (userId != null && addUserIdStr != null) {
                        try {
                            int ReceiverId = Integer.parseInt(addUserIdStr);
                            System.out.println("sender"+userId+"receiver"+ReceiverId);
                            int requestId = friendRequestCTL.addRequest(userId,ReceiverId);
                            if (requestId > 0) {
                                out.print("{\"success\": true, \"requestId\": " + requestId + ", \"message\": \"Friend request added successfully\"}");
                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("{\"success\": false, \"message\": \"Failed to add friend request\"}");
                            }
                        } catch (NumberFormatException e) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("{\"success\": false, \"message\": \"Invalid user ID\"}");
                        }
                        out.flush();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"success\": false, \"message\": \"Invalid request parameters\"}");
                        out.flush();
                    }
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"success\": false, \"message\": \"Invalid action\"}");
                    out.flush();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Đã xảy ra lỗi!\"}");
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
