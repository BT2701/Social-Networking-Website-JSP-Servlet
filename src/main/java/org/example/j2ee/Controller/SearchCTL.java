package org.example.j2ee.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.j2ee.Service.SearchSV;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;


@WebServlet("/search/*")
public class SearchCTL extends HttpServlet {
    private SearchSV searchSV;

    @Override
    public void init() throws ServletException {
        searchSV= new SearchSV();
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");
        int currentUser= 1; // lay du lieu tu cookie
        if (keyword==null){
            keyword=" ";
        }
        if (type==null){
            type="all";
        }
        HashMap<String, Object> result= searchSV.search(keyword, type, currentUser);
        request.setAttribute("users", result.get("users"));
        request.setAttribute("posts", result.get("posts"));
        request.setAttribute("friends", result.get("friends"));
        request.getRequestDispatcher("/Template/Searcher/search.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if(path !=null){
            switch (path){
                case "/addFriend":
                    addFriend(request,response);
                    break;
                case "/removeFriend":
                    removeFriend(request,response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown request path: " + path);
                    break;
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "path is required" );
        }
    }
    public void addFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);
        String friendId = jsonNode.get("friendId").asText();
        int currentUser= 1; //lấy dữ liệu từ coookie
        boolean success = searchSV.checkAddFriend(currentUser, friendId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + success + "}");
    }
    public void removeFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);
        String friendId = jsonNode.get("friendId").asText();
        int currentUser= 1;//lấy dữ liệu từ cookie
        boolean success = searchSV.checkRemoveFriend(currentUser,friendId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + success + "}");
    }

}
