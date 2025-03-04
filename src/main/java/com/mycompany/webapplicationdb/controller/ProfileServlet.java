package com.mycompany.webapplicationdb.controller;

import com.mycompany.webapplicationdb.dao.PostDAO;
import com.mycompany.webapplicationdb.model.Post;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String userName = (String) session.getAttribute("userName");
        PostDAO postDAO = new PostDAO();

        try {
            List<Post> posts = postDAO.getUserPosts(userName);
            request.setAttribute("posts", posts);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving posts.");
        }

        request.getRequestDispatcher("/view/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String userName = (String) session.getAttribute("userName");
        String action = request.getParameter("action");
        PostDAO postDAO = new PostDAO();

        try {
            if ("create".equals(action)) {
                String content = request.getParameter("content");
                boolean success = postDAO.createPost(userName, content);
                if (!success) {
                    session.setAttribute("errorMessage", "Failed to create post.");
                }
            } else if ("delete".equals(action)) {
                int postId = Integer.parseInt(request.getParameter("postId"));
                boolean success = postDAO.deletePost(postId, userName);
                if (!success) {
                    session.setAttribute("errorMessage", "Failed to delete post.");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            session.setAttribute("errorMessage", "Error processing request.");
        }

        response.sendRedirect(request.getContextPath() + "/ProfileServlet");
    }
}