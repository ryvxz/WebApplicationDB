package com.mycompany.webapplicationdb.controller.user;

import com.mycompany.webapplicationdb.dao.AccountDAO;
import com.mycompany.webapplicationdb.dao.FollowDAO;
import com.mycompany.webapplicationdb.dao.PostDAO;
import com.mycompany.webapplicationdb.model.Account;
import com.mycompany.webapplicationdb.util.ValidationUtil;
import com.mycompany.webapplicationdb.util.SessionUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {
    
    private AccountDAO accountDAO;
    private PostDAO postDAO;
    private FollowDAO followDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        accountDAO = new AccountDAO();
        postDAO = new PostDAO();
        followDAO = new FollowDAO();
    }
    
    /**
     * Handles the HTTP GET request - displays the signup form
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
        
        // Get any errors from previous signup attempt
        Map<String, String> errors = ValidationUtil.getErrorsFromSession(request);
        request.setAttribute("errors", errors);
        
        // Get any previously entered data to pre-fill the form
        Map<String, String> formData = ValidationUtil.getFormDataFromSession(request);
        request.setAttribute("formData", formData);
        
        // Forward to signup page
        request.getRequestDispatcher("/view/user/signup.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request - processes the signup form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate inputs
        Map<String, String> errors = new HashMap<>();
        
        if (!ValidationUtil.validateRequired(username, "username", errors)) {
            // Username is required - error already added to map
        } else if (!ValidationUtil.validateUsername(username, errors)) {
            // Username format is invalid - error already added to map
        } else if (accountDAO.usernameExists(username)) {
            errors.put("username", "Username already exists");
        }
        
        if (!ValidationUtil.validateRequired(password, "password", errors)) {
            // Password is required - error already added to map
        } else if (!ValidationUtil.validatePassword(password, errors)) {
            // Password format is invalid - error already added to map
        }
        
        if (!ValidationUtil.validateRequired(confirmPassword, "confirmPassword", errors)) {
            errors.put("confirmPassword", "Please confirm your password");
        } else if (!password.equals(confirmPassword)) {
            errors.put("confirmPassword", "Passwords do not match");
        }
        
        // If there are validation errors, redirect back to signup page
        if (!errors.isEmpty()) {
            // Store errors and form data in session for retrieval after redirect
            ValidationUtil.storeErrorsInSession(request, errors);
            
            Map<String, String> formData = new HashMap<>();
            formData.put("username", username);
            ValidationUtil.storeFormDataInSession(request, formData);
            
            response.sendRedirect(request.getContextPath() + "/signup");
            return;
        }
        
        // Create new account
        Account account = new Account();
        account.setUserName(username);
        account.setPassword(password);
        account.setUserRole("USER"); // Default role for new signups
        
        boolean accountCreated = accountDAO.createAccount(account);
        
        if (!accountCreated) {
            errors.put("signup", "Failed to create account. Please try again.");
            ValidationUtil.storeErrorsInSession(request, errors);
            
            Map<String, String> formData = new HashMap<>();
            formData.put("username", username);
            ValidationUtil.storeFormDataInSession(request, formData);
            
            response.sendRedirect(request.getContextPath() + "/signup");
            return;
        }
        
        // Initialize user's posts and follows records
        postDAO.initializeUserPosts(username);
        followDAO.initializeUserFollows(username);
        
        // Set success message for login page
        ValidationUtil.storeSuccessMessage(request, "Account created successfully. Please log in.");
        
        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}