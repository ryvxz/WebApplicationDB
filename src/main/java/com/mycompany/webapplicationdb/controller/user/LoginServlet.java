package com.mycompany.webapplicationdb.controller.user;

import com.mycompany.webapplicationdb.dao.AccountDAO;
import com.mycompany.webapplicationdb.model.Account;
import com.mycompany.webapplicationdb.util.ValidationUtil;
import com.mycompany.webapplicationdb.util.SessionUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private AccountDAO accountDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        accountDAO = new AccountDAO();
    }
    
    /**
     * Handles the HTTP GET request - displays the login form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // If user is already logged in, redirect to appropriate page
        if (SessionUtil.isLoggedIn(request)) {
            if (SessionUtil.isAdmin(request) || SessionUtil.isSuperAdmin(request)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/landing");
            }
            return;
        }
        
        // Get any errors from previous login attempt
        Map<String, String> errors = ValidationUtil.getErrorsFromSession(request);
        request.setAttribute("errors", errors);
        
        // Get any previously entered username to pre-fill the form
        Map<String, String> formData = ValidationUtil.getFormDataFromSession(request);
        request.setAttribute("formData", formData);
        
        // Forward to login page
        request.getRequestDispatcher("/view/user/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request - processes the login form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate inputs
        Map<String, String> errors = new HashMap<>();
        
        if (!ValidationUtil.validateRequired(username, "username", errors)) {
            // Username is required - error already added to map
        }
        
        if (!ValidationUtil.validateRequired(password, "password", errors)) {
            // Password is required - error already added to map
        }
        
        // If there are validation errors, redirect back to login page
        if (!errors.isEmpty()) {
            // Store errors and form data in session for retrieval after redirect
            ValidationUtil.storeErrorsInSession(request, errors);
            
            Map<String, String> formData = new HashMap<>();
            formData.put("username", username);
            ValidationUtil.storeFormDataInSession(request, formData);
            
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Attempt to validate user credentials
        Account account = accountDAO.validateLogin(username, password);
        
        if (account == null) {
            // Invalid credentials
            errors.put("login", "Invalid username or password");
            ValidationUtil.storeErrorsInSession(request, errors);
            
            Map<String, String> formData = new HashMap<>();
            formData.put("username", username);
            ValidationUtil.storeFormDataInSession(request, formData);
            
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Valid login - set user in session
        SessionUtil.setUser(request, account);
        
        // Redirect based on user role
        if (account.isAdmin() || account.isSuperAdmin()) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/landing");
        }
    }
}