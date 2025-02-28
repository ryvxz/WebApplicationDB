<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Users - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin" class="nav-link">Admin</a>
            <a href="${pageContext.request.contextPath}/create" class="nav-link">Create User</a>
            <a href="${pageContext.request.contextPath}/update" class="nav-link">Update User</a>
            <a href="${pageContext.request.contextPath}/delete" class="nav-link active">Delete User</a>
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
            <h1>Delete Users</h1>

            <form action="${pageContext.request.contextPath}/DeleteUserServlet" method="post">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>Username</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="checkbox" name="deleteUser" value="User1"></td>
                            <td>User1</td>
                            <td>User</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" name="deleteUser" value="AdminUser"></td>
                            <td>AdminUser</td>
                            <td>Admin</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" name="deleteUser" value="SuperAdmin"></td>
                            <td>SuperAdmin</td>
                            <td>Super Admin</td> 
                        </tr>
                    </tbody>
                </table>

                <!-- Centered delete button -->
                <div class="button-container"> <br>
                    <button type="submit" class="primary-button">Delete Selected Users</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>