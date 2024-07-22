package org.example.j2ee.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.j2ee.Service.SearchSV;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/search")
public class SearchCTL extends HttpServlet {
    private SearchSV searchSV;

    @Override
    public void init() throws ServletException {
        searchSV= new SearchSV();
    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");
        HashMap<String, Object> result= searchSV.search(keyword, type);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/Template/Searcher/search.jsp").forward(request, response);
    }
}
