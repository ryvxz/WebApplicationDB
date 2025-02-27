package com.mycompany.webapplicationdb.dao;

import com.mycompany.webapplicationdb.database.DatabaseHandler;
import com.mycompany.webapplicationdb.model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Post entities
 */
public class PostDAO {
    
    /**
     * Initialize posts record for a new user
     * 
     * @param username The username of the new user
     * @return true if creation was successful, false otherwise
     */
    public boolean initializeUserPosts(String username) {
        String sql = "INSERT INTO posts (user_name) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error initializing user posts: " + e.getMessage());
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
     * Get posts for a specific user
     * 
     * @param username The username to get posts for
     * @return The Post object containing all posts, null if not found
     */
    public Post getUserPosts(String username) {
        String sql = "SELECT * FROM posts WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Post post = new Post();
                post.setUserName(rs.getString("user_name"));
                post.setPost1(rs.getString("post1"));
                post.setPost2(rs.getString("post2"));
                post.setPost3(rs.getString("post3"));
                post.setPost4(rs.getString("post4"));
                post.setPost5(rs.getString("post5"));
                return post;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user posts: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DatabaseHandler.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return null; // No posts found
    }
    
    /**
     * Add a new post for a user
     * 
     * @param username The username
     * @param content The post content
     * @return true if addition was successful, false otherwise
     */
    public boolean addPost(String username, String content) {
        Post userPosts = getUserPosts(username);
        
        // If user has no posts record yet, initialize one
        if (userPosts == null) {
            if (!initializeUserPosts(username)) {
                return false;
            }
            userPosts = new Post();
            userPosts.setUserName(username);
        }
        
        // Add the new post, shifting others
        userPosts.addNewPost(content);
        
        // Update the database
        String sql = "UPDATE posts SET post1 = ?, post2 = ?, post3 = ?, post4 = ?, post5 = ? WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userPosts.getPost1());
            stmt.setString(2, userPosts.getPost2());
            stmt.setString(3, userPosts.getPost3());
            stmt.setString(4, userPosts.getPost4());
            stmt.setString(5, userPosts.getPost5());
            stmt.setString(6, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding post: " + e.getMessage());
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
     * Delete a specific post for a user
     * 
     * @param username The username
     * @param postIndex The index of the post (1-5)
     * @return true if deletion was successful, false otherwise
     */
    public boolean deletePost(String username, int postIndex) {
        if (postIndex < 1 || postIndex > 5) {
            return false; // Invalid post index
        }
        
        String columnName = "post" + postIndex;
        String sql = "UPDATE posts SET " + columnName + " = NULL WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting post: " + e.getMessage());
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
     * Get posts from users being followed
     * 
     * @param follows List of usernames being followed
     * @return List of posts from followed users
     */
    public List<String> getPostsFromFollowedUsers(List<String> follows) {
        List<String> allPosts = new ArrayList<>();
        
        if (follows == null || follows.isEmpty()) {
            return allPosts; // No follows, return empty list
        }
        
        for (String followedUser : follows) {
            if (followedUser == null || followedUser.isEmpty()) continue;
            
            Post userPosts = getUserPosts(followedUser);
            if (userPosts != null) {
                List<String> posts = userPosts.getPostsAsList();
                for (String post : posts) {
                    if (post != null && !post.isEmpty()) {
                        allPosts.add(followedUser + ": " + post);
                    }
                }
            }
        }
        
        return allPosts;
    }
    
    /**
     * Delete all posts for a user
     * 
     * @param username The username
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAllPosts(String username) {
        String sql = "DELETE FROM posts WHERE user_name = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseHandler.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting all posts: " + e.getMessage());
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