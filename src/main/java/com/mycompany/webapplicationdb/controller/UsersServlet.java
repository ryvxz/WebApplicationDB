package com.mycompany.webapplicationdb.controller;

import com.mycompany.webapplicationdb.dao.FollowDAO;
import com.mycompany.webapplicationdb.dao.UserDAO;
import com.mycompany.webapplicationdb.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
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
        FollowDAO followDAO = new FollowDAO();
        UserDAO userDAO = new UserDAO();

        try {
            List<User> followedUsers = followDAO.getFollowedUsers(userName);
            List<User> allUsers = userDAO.getAllUsers();

            request.setAttribute("followedUsers", followedUsers);
            request.setAttribute("allUsers", allUsers);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving users.");
        }

        request.getRequestDispatcher("/view/users.jsp").forward(request, response);
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
        String targetUser = request.getParameter("targetUser");
        FollowDAO followDAO = new FollowDAO();
        UserDAO userDAO = new UserDAO();

        try {
            String resultMessage = "";
            if ("follow".equals(action)) {
                // Follow the user and capture the result message
                resultMessage = followDAO.followUser(userName, targetUser);
            } else if ("unfollow".equals(action)) {
                // Unfollow the user and capture the result message
                resultMessage = followDAO.unfollowUser(userName, targetUser);
            }

            // Set the result message to be displayed on JSP
            request.setAttribute("message", resultMessage);

            // Always set the followed users list, even after follow/unfollow attempt
            List<User> followedUsers = followDAO.getFollowedUsers(userName);
            request.setAttribute("followedUsers", followedUsers);

            // Optionally, pass the list of all users if you want to display that in the JSP
            List<User> allUsers = userDAO.getAllUsers();
            request.setAttribute("allUsers", allUsers);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error processing follow/unfollow request.");
        }

        // Forward the request to users.jsp with the message and followed users list
        request.getRequestDispatcher("/view/users.jsp").forward(request, response);
    }
}
