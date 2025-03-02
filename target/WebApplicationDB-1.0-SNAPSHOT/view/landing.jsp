<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mycompany.webapplicationdb.model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home - WebApplicationDB</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/LandingServlet">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
            </a>
        </div>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/LandingServlet">Home</a>
            <a href="${pageContext.request.contextPath}/ProfileServlet">Profile</a>
            <a href="${pageContext.request.contextPath}/UsersServlet">Users</a>
            <a href="${pageContext.request.contextPath}/view/help.jsp">Help</a>
        </div>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout">Logout</a>
    </div>
<div class="container">
        <h2>Home</h2>
        
        <% if (request.getAttribute("followedPosts") != null) { %>
            <% List<Post> followedPosts = (List<Post>) request.getAttribute("followedPosts"); %>
            <% if (followedPosts.isEmpty()) { %>
                <p>No posts to display from followed users.</p>
            <% } else { %>
                <% for (Post post : followedPosts) { %>
                    <div class="post">
                        <p><strong><%= post.getUserName() %></strong></p>
                        <p><%= post.getPostContent() %></p>
                        <p class="timestamp"><%= post.getPostDate() %></p>
                    </div>
                <% } %>
            <% } %>
        <% } else { %>
            <p>No posts available.</p>
        <% } %>
    </div>
</body>
</html>
