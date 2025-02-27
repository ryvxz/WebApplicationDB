package com.mycompany.webapplicationdb.dao;

import com.mycompany.webapplicationdb.database.DatabaseHandler;
import com.mycompany.webapplicationdb.model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Account entities
 */
public class AccountDAO {
    
    /**
     * Create a new account in the database
     * 
     * @param account The account to create
     * @return true if creation was successful, false otherwise
     */
    public boolean createAccount(Account account) {
        String sql = "INSERT INTO account (user_name, password, user_role) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getUserName());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getUserRole());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get an account by username
     * 
     * @param username The username to search for
     * @return The Account object if found, null otherwise
     */
    public Account getAccount(String username) {
        String sql = "SELECT * FROM account WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setUserRole(rs.getString("user_role"));
                return account;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting account: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return null; // User not found
    }
    
    /**
     * Update an existing account
     * 
     * @param account The account with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateAccount(Account account) {
        String sql = "UPDATE account SET password = ?, user_role = ? WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getPassword());
            stmt.setString(2, account.getUserRole());
            stmt.setString(3, account.getUserName());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating account: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Update an account with selective field updates
     * 
     * @param username The username of the account to update
     * @param password New password (null to keep unchanged)
     * @param userRole New user role (null to keep unchanged)
     * @return true if update was successful, false otherwise
     */
    public boolean updateAccountSelective(String username, String password, String userRole) {
        Account account = getAccount(username);
        if (account == null) return false;
        
        if (password != null && !password.trim().isEmpty()) {
            account.setPassword(password);
        }
        
        if (userRole != null && !userRole.trim().isEmpty()) {
            account.setUserRole(userRole);
        }
        
        return updateAccount(account);
    }
    
    /**
     * Delete an account by username
     * 
     * @param username The username of the account to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAccount(String username) {
        String sql = "DELETE FROM account WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Validate user credentials
     * 
     * @param username The username
     * @param password The password
     * @return The Account if credentials are valid, null otherwise
     */
    public Account validateLogin(String username, String password) {
        String sql = "SELECT * FROM account WHERE user_name = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setUserRole(rs.getString("user_role"));
                return account;
            }
            
        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return null; // Invalid credentials
    }
    
    /**
     * Check if a username exists
     * 
     * @param username The username to check
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM account WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            return rs.next(); // true if username exists
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get all user accounts
     * 
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account ORDER BY user_name";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setUserRole(rs.getString("user_role"));
                accounts.add(account);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all accounts: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return accounts;
    }
    
    /**
     * Get all regular users (not admins)
     * 
     * @return List of regular user accounts
     */
    public List<Account> getAllUsers() {
        List<Account> users = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_role = 'USER' ORDER BY user_name";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setUserRole(rs.getString("user_role"));
                users.add(account);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return users;
    }
    
    /**
     * Get all admin users
     * 
     * @return List of admin accounts
     */
    public List<Account> getAllAdmins() {
        List<Account> admins = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_role = 'ADMIN' OR user_role = 'SUPER_ADMIN' ORDER BY user_name";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setUserRole(rs.getString("user_role"));
                admins.add(account);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all admins: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return admins;
    }
}