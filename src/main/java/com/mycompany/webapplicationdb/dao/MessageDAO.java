package com.mycompany.webapplicationdb.dao;

import com.mycompany.webapplicationdb.database.DatabaseHandler;
import com.mycompany.webapplicationdb.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Message entities
 * Handles database operations related to help messages
 */
public class MessageDAO {
    
    /**
     * Insert a new message into the database
     * 
     * @param message The message to insert
     * @return true if successful, false otherwise
     */
    public boolean createMessage(Message message) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "INSERT INTO messages (user_name, message) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, message.getUserName());
            stmt.setString(2, message.getMessage());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating message: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
    }
    
    /**
     * Get the latest messages for admin view
     * 
     * @param limit Maximum number of messages to retrieve
     * @return List of messages ordered by timestamp (newest first)
     */
    public List<Message> getLatestMessages(int limit) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "SELECT id, user_name, message, timestamp FROM messages "
                       + "ORDER BY timestamp DESC LIMIT ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, limit);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp")
                );
                messages.add(message);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving messages: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
        
        return messages;
    }
    
    /**
     * Delete a message by its ID
     * 
     * @param id ID of the message to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteMessage(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "DELETE FROM messages WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting message: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
    }
    
    /**
     * Get a specific message by its ID
     * 
     * @param id ID of the message to retrieve
     * @return Message object if found, null otherwise
     */
    public Message getMessageById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Message message = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "SELECT id, user_name, message, timestamp FROM messages WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                message = new Message(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving message: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
        
        return message;
    }
    
    /**
     * Count the total number of messages in the database
     * 
     * @return Count of messages
     */
    public int countMessages() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "SELECT COUNT(*) FROM messages";
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting messages: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
        
        return count;
    }
    
    /**
     * Get all messages from a specific user
     * 
     * @param userName Username to filter by
     * @return List of messages from the specified user
     */
    public List<Message> getMessagesByUser(String userName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "SELECT id, user_name, message, timestamp FROM messages "
                       + "WHERE user_name = ? ORDER BY timestamp DESC";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userName);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp")
                );
                messages.add(message);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving user messages: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
        
        return messages;
    }
    
    /**
     * Delete all messages from a specific user
     * Useful when deleting a user account
     * 
     * @param userName Username whose messages should be deleted
     * @return Number of messages deleted
     */
    public int deleteMessagesByUser(String userName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            
            String sql = "DELETE FROM messages WHERE user_name = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userName);
            
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting user messages: " + e.getMessage());
            return 0;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            DatabaseHandler.closeConnection(conn);
        }
    }
}