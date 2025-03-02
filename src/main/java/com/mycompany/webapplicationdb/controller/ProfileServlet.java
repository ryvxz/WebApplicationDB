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
            // Always fetch posts when accessing the page
            List<Post> posts = postDAO.getUserPosts(userName);
            request.setAttribute("posts", posts);
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
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
                // Fetch current number of posts
                List<Post> posts = postDAO.getUserPosts(userName);
                
                if (posts.size() >= 5) {
                    session.setAttribute("errorMessage", "You cannot have more than 5 posts. Delete an old post first.");
                } else {
                    String content = request.getParameter("content");
                    if (content == null || content.trim().isEmpty() || content.length() > 200) {
                        session.setAttribute("errorMessage", "Post content must be between 1 and 200 characters.");
                    } else {
                        postDAO.createPost(userName, content);
                    }
                }
            } else if ("delete".equals(action)) {
                int postId = Integer.parseInt(request.getParameter("postId"));
                postDAO.deletePost(postId, userName);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error processing post request.");
        }

        // Redirect to ensure fresh page load with updated posts
        response.sendRedirect(request.getContextPath() + "/ProfileServlet");
    }
}