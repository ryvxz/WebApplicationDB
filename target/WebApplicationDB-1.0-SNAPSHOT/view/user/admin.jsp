<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin" class="nav-link active">Admin</a>
            <a href="${pageContext.request.contextPath}/create" class="nav-link">Create User</a>
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
            <h1>User Management</h1>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>SampleUser1</td>
                        <td>User</td>
                    </tr>
                    <tr>
                        <td>SampleAdmin</td>
                        <td>Admin</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="content">
            <h1>Latest Messages</h1>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>From</th>
                        <th>Message</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User123</td>
                        <td>Need help with my account.</td>
                    </tr>
                    <tr>
                        <td>User456</td>
                        <td>Requesting access to a new feature.</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>