<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Update User</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
        <div class="navbar">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/AdminServlet">
                    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
                </a>
            </div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/AdminServlet">Admin Dashboard</a>
                <a href="${pageContext.request.contextPath}/view/create.jsp">Create User</a>
                <a href="${pageContext.request.contextPath}/view/update.jsp">Update User</a>
                <a href="${pageContext.request.contextPath}/view/delete.jsp">Delete User</a>
                <a href="${pageContext.request.contextPath}/view/result.jsp">View Results</a>
            </div>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout">Logout</a>
        </div>
        <div class="container">
            <h2>Update User Information</h2>
            <form action="${pageContext.request.contextPath}/UpdateUserServlet" method="post">
                <h3>Update User</h3>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required><br>

                <label for="password">New Password:</label>
                <input type="password" id="password" name="password"><br>

                <label for="user_role">New Role:</label>
                <select id="user_role" name="user_role">
                    <option value="user">User</option>
                    <option value="admin">Admin</option>
                </select><br>

                <button type="submit">Update</button>
            </form>

        </div>
    </body>
</html>