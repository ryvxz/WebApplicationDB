package com.mycompany.webapplicationdb.util;

import com.mycompany.webapplicationdb.model.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for user session management
 */
public class SessionUtil {
    
    private static final String SESSION_USER = "currentUser";
    private static final int SESSION_TIMEOUT = 30 * 60; // 30 minutes in seconds
    
    /**
     * Set the current user in the session
     * 
     * @param request The HTTP request
     * @param user The user to store in session
     */
    public static void setUser(HttpServletRequest request, Account user) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER, user);
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
    }
    
    /**
     * Get the current user from the session
     * 
     * @param request The HTTP request
     * @return The current user, or null if not logged in
     */
    public static Account getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Account) session.getAttribute(SESSION_USER);
    }
    
    /**
     * Check if a user is logged in
     * 
     * @param request The HTTP request
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }
    
    /**
     * Check if the current user has admin privileges
     * 
     * @param request The HTTP request
     * @return true if user is an admin, false otherwise
     */
    public static boolean isAdmin(HttpServletRequest request) {
        Account user = getUser(request);
        return user != null && user.isAdmin();
    }
    
    /**
     * Check if the current user has super admin privileges
     * 
     * @param request The HTTP request
     * @return true if user is a super admin, false otherwise
     */
    public static boolean isSuperAdmin(HttpServletRequest request) {
        Account user = getUser(request);
        return user != null && user.isSuperAdmin();
    }
    
    /**
     * Invalidate the user session (logout)
     * 
     * @param request The HTTP request
     */
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    /**
     * Get the username of the current user
     * 
     * @param request The HTTP request
     * @return The username, or null if not logged in
     */
    public static String getUsername(HttpServletRequest request) {
        Account user = getUser(request);
        return (user != null) ? user.getUserName() : null;
    }
    
    /**
     * Reset the session timeout
     * 
     * @param request The HTTP request
     */
    public static void refreshSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(SESSION_TIMEOUT);
        }
    }
    
    /**
     * Check if the user has permission to access a specific page
     * 
     * @param request The HTTP request
     * @param requiresAdmin true if the page requires admin access
     * @param requiresSuperAdmin true if the page requires super admin access
     * @return true if user has permission, false otherwise
     */
    public static boolean hasPermission(HttpServletRequest request, boolean requiresAdmin, boolean requiresSuperAdmin) {
        if (!isLoggedIn(request)) {
            return false;
        }
        
        if (requiresSuperAdmin) {
            return isSuperAdmin(request);
        }
        
        if (requiresAdmin) {
            return isAdmin(request);
        }
        
        return true; // Regular user access
    }
    
    /**
     * Store the requested URL for redirect after login
     * 
     * @param request The HTTP request
     * @param url The URL to store
     */
    public static void setRedirectUrl(HttpServletRequest request, String url) {
        HttpSession session = request.getSession();
        session.setAttribute("redirectUrl", url);
    }
    
    /**
     * Get the stored redirect URL
     * 
     * @param request The HTTP request
     * @return The redirect URL, or null if not set
     */
    public static String getRedirectUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        
        String url = (String) session.getAttribute("redirectUrl");
        if (url != null) {
            // Clear after retrieving
            session.removeAttribute("redirectUrl");
        }
        return url;
    }
}