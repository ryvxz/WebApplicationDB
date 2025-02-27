package com.mycompany.webapplicationdb.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for form validation and error handling
 */
public class ValidationUtil {
    
    /**
     * Validates that a field is not empty
     * 
     * @param value The value to check
     * @param fieldName The name of the field for error messages
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validateRequired(String value, String fieldName, Map<String, String> errors) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(fieldName, fieldName + " is required");
            return false;
        }
        return true;
    }
    
    /**
     * Validates that a username meets requirements (alphanumeric, 3-50 chars)
     * 
     * @param username The username to validate
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validateUsername(String username, Map<String, String> errors) {
        if (!validateRequired(username, "username", errors)) {
            return false;
        }
        
        if (username.length() < 3 || username.length() > 50) {
            errors.put("username", "Username must be between 3 and 50 characters");
            return false;
        }
        
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            errors.put("username", "Username must contain only letters, numbers, and underscores");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates that a password meets requirements (min 6 chars)
     * 
     * @param password The password to validate
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validatePassword(String password, Map<String, String> errors) {
        if (!validateRequired(password, "password", errors)) {
            return false;
        }
        
        if (password.length() < 6) {
            errors.put("password", "Password must be at least 6 characters");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates that a post meets requirements (not empty, max 200 chars)
     * 
     * @param postContent The post content to validate
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validatePost(String postContent, Map<String, String> errors) {
        if (!validateRequired(postContent, "post", errors)) {
            return false;
        }
        
        if (postContent.length() > 200) {
            errors.put("post", "Post cannot exceed 200 characters");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates that a message meets requirements (not empty)
     * 
     * @param message The message to validate
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validateMessage(String message, Map<String, String> errors) {
        return validateRequired(message, "message", errors);
    }
    
    /**
     * Validates that a user role is valid
     * 
     * @param role The role to validate
     * @param errors Map to store error messages
     * @return true if valid, false otherwise
     */
    public static boolean validateRole(String role, Map<String, String> errors) {
        if (!validateRequired(role, "role", errors)) {
            return false;
        }
        
        if (!role.equals("USER") && !role.equals("ADMIN") && !role.equals("SUPER_ADMIN")) {
            errors.put("role", "Invalid role specified");
            return false;
        }
        
        return true;
    }
    
    /**
     * Helper method to store errors in session and prepare for redirect
     * 
     * @param request The HTTP request
     * @param errors Map containing error messages
     */
    public static void storeErrorsInSession(HttpServletRequest request, Map<String, String> errors) {
        request.getSession().setAttribute("errors", errors);
    }
    
    /**
     * Helper method to retrieve errors from session
     * 
     * @param request The HTTP request
     * @return Map containing error messages, or empty map if none
     */
    public static Map<String, String> getErrorsFromSession(HttpServletRequest request) {
        Map<String, String> errors = (Map<String, String>) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new HashMap<>();
        } else {
            // Clear errors from session after retrieving
            request.getSession().removeAttribute("errors");
        }
        return errors;
    }
    
    /**
     * Helper method to store form data in session for redisplay after validation failure
     * 
     * @param request The HTTP request
     * @param formData Map containing form data
     */
    public static void storeFormDataInSession(HttpServletRequest request, Map<String, String> formData) {
        request.getSession().setAttribute("formData", formData);
    }
    
    /**
     * Helper method to retrieve form data from session
     * 
     * @param request The HTTP request
     * @return Map containing form data, or empty map if none
     */
    public static Map<String, String> getFormDataFromSession(HttpServletRequest request) {
        Map<String, String> formData = (Map<String, String>) request.getSession().getAttribute("formData");
        if (formData == null) {
            formData = new HashMap<>();
        } else {
            // Clear form data from session after retrieving
            request.getSession().removeAttribute("formData");
        }
        return formData;
    }
    
    /**
     * Helper method to store a success message in session
     * 
     * @param request The HTTP request
     * @param message The success message
     */
    public static void storeSuccessMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("successMessage", message);
    }
    
    /**
     * Helper method to retrieve a success message from session
     * 
     * @param request The HTTP request
     * @return The success message, or null if none
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("successMessage");
        if (message != null) {
            // Clear message from session after retrieving
            request.getSession().removeAttribute("successMessage");
        }
        return message;
    }
}