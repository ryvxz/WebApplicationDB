<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mycompany.webapplicationdb.model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile - WebApplicationDB</title>
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
            <a href="${pageContext.request.contextPath}/view/profile.jsp">Profile</a>
            <a href="${pageContext.request.contextPath}/view/users.jsp">Users</a>
            <a href="${pageContext.request.contextPath}/view/help.jsp">Help</a>
        </div>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout">Logout</a>
    </div>
    
    <div class="container">
        <h2>Your Profile</h2>

        <!-- Display error message if it exists -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <h3>Create a Post</h3>
        <form action="${pageContext.request.contextPath}/ProfileServlet" method="post">
            <textarea name="content" placeholder="Write something..." maxlength="200" required></textarea>
            <input type="hidden" name="action" value="create">
            <button type="submit">Post</button>
        </form>
            <br>
        <h3>Your Posts</h3>
        <% if (request.getAttribute("posts") != null) { %>
            <% List<Post> posts = (List<Post>) request.getAttribute("posts"); %>
            <% if (posts.isEmpty()) { %>
                <p>No posts yet.</p>
            <% } else { %>
                <% for (Post post : posts) { %>
                    <div class="post">
                        <p><%= post.getPostContent() %></p>
                        <p class="timestamp"><%= post.getPostDate() %></p>
                        <form action="${pageContext.request.contextPath}/ProfileServlet" method="post">
                            <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit">Delete</button>
                        </form>
                    </div>
                <% } %>
            <% } %>
        <% } else { %>
            <p>No posts available.</p>
        <% } %>
    </div>
</body>
</html>
