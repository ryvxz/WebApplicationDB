<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Users - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin" class="nav-link">Admin</a>
            <a href="${pageContext.request.contextPath}/create" class="nav-link">Create User</a>
            <a href="${pageContext.request.contextPath}/update" class="nav-link active">Update User</a>
            <a href="${pageContext.request.contextPath}/delete" class="nav-link">Delete User</a>
            <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
        </div>
        <div class="user-info">
            Logged in as: ${sessionScope.user.userName} (Admin)
            <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                <button type="submit" class="logout-button">Logout</button>
            </form>
        </div>
    </header>

    <div class="container">
        <div class="content">
            <h1>Update Users</h1>
            <form action="${pageContext.request.contextPath}/UpdateUserServlet" method="post">
                
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword">
                </div>

                <div class="form-group">
                    <label for="newRole">New Role:</label>
                    <select id="newRole" name="newRole">
                        <option value="">Select Role</option>
                        <option value="user">User</option>
                        <option value="admin">Admin</option>
                        <option value="super_admin">Super Admin</option>
                    </select>
                </div>

                <!-- Centered update button -->
                <div class="button-container">
                    <button type="submit" class="primary-button">Update User</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>