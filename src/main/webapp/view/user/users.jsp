<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Follows - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/landing" class="logo">WebApplicationDB</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/landing" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            <a href="${pageContext.request.contextPath}/users" class="nav-link active">Users</a>
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
            <h1>Manage Followed Users</h1>
            
            <h3>Currently Following</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>FollowedUser1</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/UnfollowServlet" method="post">
                                <input type="hidden" name="unfollowUser" value="FollowedUser1">
                                <button type="submit" class="secondary-button">Unfollow</button>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>FollowedUser2</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/UnfollowServlet" method="post">
                                <input type="hidden" name="unfollowUser" value="FollowedUser2">
                                <button type="submit" class="secondary-button">Unfollow</button>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="content">
            <h3>Follow a New User</h3>
            <form action="${pageContext.request.contextPath}/FollowServlet" method="post">
                <div class="form-group">
                    <label for="followUsername">Username:</label>
                    <input type="text" id="followUsername" name="followUsername" required>
                </div>
                <div class="button-container">
                    <button type="submit" class="primary-button">Follow</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>