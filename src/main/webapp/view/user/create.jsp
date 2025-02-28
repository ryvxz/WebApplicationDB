<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create User - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin" class="nav-link">Admin</a>
            <a href="${pageContext.request.contextPath}/create" class="nav-link active">Create User</a>
            <a href="${pageContext.request.contextPath}/update" class="nav-link">Update User</a>
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
            <h1>Create New User</h1>
            <form action="${pageContext.request.contextPath}/CreateUserServlet" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="role">User Role:</label>
                    <select id="role" name="role" required>
                        <option value="user">User</option>
                        <option value="admin">Admin</option>
                        <option value="super_admin">Super Admin</option>
                    </select>
                </div>

                <button type="submit" class="primary-button">Create User</button>
            </form>
        </div>
    </div>
</body>
</html>