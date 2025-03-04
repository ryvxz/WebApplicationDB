package com.mycompany.webapplicationdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.webapplicationdb.model.Post;
import com.mycompany.webapplicationdb.util.DBConnection;

public class PostDAO {

    public boolean createPost(String userName, String content) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            // Ensure user has at most 5 posts
            PreparedStatement countSt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM posts WHERE user_name = ?");
            countSt.setString(1, userName);
            ResultSet countRs = countSt.executeQuery();
            countRs.next();
            int postCount = countRs.getInt(1);

            if (postCount >= 5) {
                // Delete the oldest post to make space for a new one
                PreparedStatement deleteSt = conn.prepareStatement(
                        "DELETE FROM posts WHERE post_id = (SELECT post_id FROM posts WHERE user_name = ? ORDER BY post_date ASC LIMIT 1)");
                deleteSt.setString(1, userName);
                deleteSt.executeUpdate();
            }

            // Insert the new post
            PreparedStatement insertSt = conn.prepareStatement(
                    "INSERT INTO posts(user_name, post_content, post_order) VALUES (?, ?, ?)");
            insertSt.setString(1, userName);
            insertSt.setString(2, content);
            insertSt.setInt(3, Math.min(postCount + 1, 5)); // Keep order within limit
            int rowsInserted = insertSt.executeUpdate();

            conn.commit();
            return rowsInserted > 0;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<Post> getUserPosts(String userName) throws SQLException {
        List<Post> posts = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM posts WHERE user_name = ? ORDER BY post_date DESC");
        st.setString(1, userName);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserName(rs.getString("user_name"));
            post.setPostContent(rs.getString("post_content"));
            post.setPostDate(rs.getTimestamp("post_date"));
            posts.add(post);
        }

        return posts;
    }

    public boolean deletePost(int postId, String userName) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement deleteSt = conn.prepareStatement(
                    "DELETE FROM posts WHERE post_id = ? AND user_name = ?");
            deleteSt.setInt(1, postId);
            deleteSt.setString(2, userName);
            int rowsDeleted = deleteSt.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Post> getFollowedUsersPosts(String userName) throws SQLException {
        List<Post> posts = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT p.* FROM posts p "
                   + "JOIN follows f ON p.user_name = f.follow1 OR p.user_name = f.follow2 OR p.user_name = f.follow3 "
                   + "WHERE f.user_name = ? "
                   + "ORDER BY p.post_date DESC";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setUserName(rs.getString("user_name"));
                post.setPostContent(rs.getString("post_content"));
                post.setPostDate(rs.getTimestamp("post_date"));
                posts.add(post);
            }
        }
        return posts;
    }
}