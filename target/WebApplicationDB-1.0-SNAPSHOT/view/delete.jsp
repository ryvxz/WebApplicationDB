<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete User</title>
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
        </div>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout">Logout</a>
    </div>
        <div class="container">
    <h2>Delete User</h2>
    <form action="${pageContext.request.contextPath}/DeleteUserServlet" method="post">
        Username: <input type="text" name="username" required><br>
        <button type="submit">Delete</button>
    </form>
        </div>
</body>
</html>