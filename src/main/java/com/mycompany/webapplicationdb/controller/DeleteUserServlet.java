package com.mycompany.webapplicationdb.controller;

import com.mycompany.webapplicationdb.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null || 
            (!"admin".equals(session.getAttribute("userRole")) && !"super_admin".equals(session.getAttribute("userRole")))) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String userName = request.getParameter("username"); // Get username from form input
        if (userName == null || userName.trim().isEmpty()) {
            session.setAttribute("message", "Username cannot be empty.");
            response.sendRedirect(request.getContextPath() + "/view/result.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        String deletionResult = userDAO.deleteUser(userName); // Call delete method

        session.setAttribute("message", deletionResult); // Store result message
        response.sendRedirect(request.getContextPath() + "/view/result.jsp"); // Redirect to result page
    }
}
