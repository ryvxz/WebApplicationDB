<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 16px;
            background-color: white;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
        }
        .logo {
            color: #4285f4;
            font-size: 20px;
            font-weight: bold;
            text-decoration: none;
        }
        .nav-links {
            display: flex;
            gap: 20px;
        }
        .nav-link {
            color: #5f6368;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s;
        }
        .nav-link:hover {
            color: #4285f4;
        }
        .nav-link.active {
            color: #4285f4;
            font-weight: bold;
        }
        .user-info {
            font-size: 14px;
            color: #5f6368;
        }
        .logout-button {
            background: none;
            border: none;
            color: #5f6368;
            cursor: pointer;
            font-size: 14px;
            text-decoration: underline;
            padding: 0;
            margin-left: 10px;
        }
        .logout-button:hover {
            color: #4285f4;
        }
        .content {
            margin-top: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h1 {
            color: #202124;
            font-size: 24px;
            margin-bottom: 20px;
            font-weight: normal;
        }
        .posts-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .post {
            padding: 15px;
            border: 1px solid #dadce0;
            border-radius: 8px;
            background-color: #f8f9fa;
        }
        .post-content {
            margin-bottom: 10px;
            line-height: 1.5;
        }
        .post-author {
            font-size: 14px;
            color: #5f6368;
            text-align: right;
        }
        .empty-state {
            text-align: center;
            color: #5f6368;
            padding: 30px 0;
        }
    </style>
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