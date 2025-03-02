package com.mycompany.webapplicationdb.controller;

import com.mycompany.webapplicationdb.dao.UserDAO;
import com.mycompany.webapplicationdb.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        boolean isValidUser = userDAO.login(userName, password);

        if (isValidUser)
        {
            User user = userDAO.getUserByUserName(userName);
            HttpSession session = request.getSession();
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("userRole", user.getUserRole());

            if ("admin".equals(user.getUserRole()) || "super_admin".equals(user.getUserRole()))
            {
                response.sendRedirect(request.getContextPath() + "/AdminServlet");
            } else 
            {
                response.sendRedirect(request.getContextPath() + "/LandingServlet");
            }
            
        } else {
            
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        }    
    }
    
}
