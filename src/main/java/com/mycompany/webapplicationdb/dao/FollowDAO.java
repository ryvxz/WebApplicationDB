package com.mycompany.webapplicationdb.dao;

import com.mycompany.webapplicationdb.database.DatabaseHandler;
import com.mycompany.webapplicationdb.model.Follow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object for Follow entities
 */
public class FollowDAO {
    
    /**
     * Initialize follows record for a new user
     * 
     * @param username The username of the new user
     * @return true if creation was successful, false otherwise
     */
    public boolean initializeUserFollows(String username) {
        String sql = "INSERT INTO follows (user_name) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error initializing user follows: " + e.getMessage());
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
     * Get follows for a specific user
     * 
     * @param username The username to get follows for
     * @return The Follow object containing all follows, null if not found
     */
    public Follow getUserFollows(String username) {
        String sql = "SELECT * FROM follows WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Follow follow = new Follow();
                follow.setUserName(rs.getString("user_name"));
                follow.setFollow1(rs.getString("follow1"));
                follow.setFollow2(rs.getString("follow2"));
                follow.setFollow3(rs.getString("follow3"));
                return follow;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user follows: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return null; // No follows found
    }
    
    /**
     * Add a follow for a user
     * 
     * @param username The username
     * @param followUsername The username to follow
     * @return true if addition was successful, false otherwise
     */
    public boolean addFollow(String username, String followUsername) {
        // Check if trying to follow self
        if (username.equals(followUsername)) {
            return false;
        }
        
        Follow userFollows = getUserFollows(username);
        
        // If user has no follows record yet, initialize one
        if (userFollows == null) {
            if (!initializeUserFollows(username)) {
                return false;
            }
            userFollows = new Follow();
            userFollows.setUserName(username);
        }
        
        // Check if already following
        if (userFollows.isFollowing(followUsername)) {
            return false;
        }
        
        // Try to add the follow
        if (!userFollows.addFollow(followUsername)) {
            return false; // No space available
        }
        
        // Update the database
        String sql = "UPDATE follows SET follow1 = ?, follow2 = ?, follow3 = ? WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userFollows.getFollow1());
            stmt.setString(2, userFollows.getFollow2());
            stmt.setString(3, userFollows.getFollow3());
            stmt.setString(4, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding follow: " + e.getMessage());
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
     * Remove a follow for a user
     * 
     * @param username The username
     * @param followUsername The username to unfollow
     * @return true if removal was successful, false otherwise
     */
    public boolean removeFollow(String username, String followUsername) {
        Follow userFollows = getUserFollows(username);
        
        if (userFollows == null) {
            return false; // No follows record
        }
        
        // Check if actually following
        if (!userFollows.isFollowing(followUsername)) {
            return false;
        }
        
        // Remove the follow
        userFollows.removeFollow(followUsername);
        
        // Update the database
        String sql = "UPDATE follows SET follow1 = ?, follow2 = ?, follow3 = ? WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            // Handle null values in prepared statement
            if (userFollows.getFollow1() == null) {
                stmt.setNull(1, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(1, userFollows.getFollow1());
            }
            
            if (userFollows.getFollow2() == null) {
                stmt.setNull(2, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(2, userFollows.getFollow2());
            }
            
            if (userFollows.getFollow3() == null) {
                stmt.setNull(3, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(3, userFollows.getFollow3());
            }
            
            stmt.setString(4, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing follow: " + e.getMessage());
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
     * Get list of usernames that a user is following
     * 
     * @param username The username
     * @return List of usernames being followed
     */
    public List<String> getFollowedUsers(String username) {
        Follow userFollows = getUserFollows(username);
        
        if (userFollows == null) {
            // If no follows record exists, initialize one
            initializeUserFollows(username);
            userFollows = new Follow();
            userFollows.setUserName(username);
        }
        
        return userFollows.getFollowsAsList();
    }
    
    /**
     * Delete all follows for a user
     * 
     * @param username The username
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAllFollows(String username) {
        String sql = "DELETE FROM follows WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting all follows: " + e.getMessage());
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
     * Update references to a deleted user
     * 
     * @param username The username that was deleted
     * @return true if update was successful
     */
    public boolean cleanupDeletedUserReferences(String username) {
        String sql = "UPDATE follows SET " +
                     "follow1 = CASE WHEN follow1 = ? THEN NULL ELSE follow1 END, " +
                     "follow2 = CASE WHEN follow2 = ? THEN NULL ELSE follow2 END, " +
                     "follow3 = CASE WHEN follow3 = ? THEN NULL ELSE follow3 END";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error cleaning up deleted user references: " + e.getMessage());
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
}