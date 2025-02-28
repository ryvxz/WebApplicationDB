<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help & Support - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/landing" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            <a href="${pageContext.request.contextPath}/users" class="nav-link">Users</a>
            <a href="${pageContext.request.contextPath}/help" class="nav-link active">Help</a>
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
            <h1>Help & Support</h1>
            <h3>Contact Admin</h3>
            <form action="${pageContext.request.contextPath}/HelpServlet" method="post">
                <div class="form-group">
                    <label for="message">Your Message:</label>
                    <textarea id="message" name="message" required></textarea>
                </div>
                <div class="button-container">
                    <button type="submit" class="primary-button">Send</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>