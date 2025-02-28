<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - WebApplicationDB</title>
    <link rel="stylesheet" href="/WebApplicationDB/css/user.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/landing" class="nav-link active">Home</a>
            <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            <a href="${pageContext.request.contextPath}/users" class="nav-link">Users</a>
            <a href="${pageContext.request.contextPath}/help" class="nav-link">Help</a>
        </div>
        <div class="user-info">
            Logged in as: ${sessionScope.user.userName}
            <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                <button type="submit" class="logout-button">Logout</button>
            </form>
        </div>
    </header>
    
    <div class="container">
        <div class="content">
            <h1>Latest Posts</h1>
            
            <div class="posts-container">
                <c:choose>
                    <c:when test="${not empty posts}">
                        <c:forEach var="post" items="${posts}">
                            <div class="post">
                                <div class="post-content">${post}</div>
                                <%-- Extract username from post for displaying author --%>
                                <div class="post-author">
                                    - ${post.substring(0, post.indexOf(':'))}
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">
                            No posts to display. Start by following some users!
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>