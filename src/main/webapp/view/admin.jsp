<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mycompany.webapplicationdb.model.User, com.mycompany.webapplicationdb.model.HelpMessage" %>
<%
    String role = (String) session.getAttribute("userRole");
    if (role == null || (!role.equals("admin") && !role.equals("super_admin")))
    {
        response.sendRedirect(request.getContextPath() + "/LandingServlet");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Panel - WebApplicationDB</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/LandingServlet">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
            </a>
        </div>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/view/admin.jsp">Admin Dashboard</a>
            <a href="${pageContext.request.contextPath}/view/create.jsp">Create User</a>
            <a href="${pageContext.request.contextPath}/view/update.jsp">Update User</a>
            <a href="${pageContext.request.contextPath}/view/delete.jsp">Delete User</a>
            <a href="${pageContext.request.contextPath}/view/result.jsp">View Results</a>
        </div>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout">Logout</a>
    </div>

        <div class="container">
            <h2>Admin Panel</h2>

            <h3>Manage Users</h3>
            <a href="${pageContext.request.contextPath}/view/create.jsp">Create User</a> |
            <a href="${pageContext.request.contextPath}/view/update.jsp">Update User</a> |
            <a href="${pageContext.request.contextPath}/view/delete.jsp">Delete User</a> 
            <br><br>
            <h3>Latest Messages</h3> 
            <% if (request.getAttribute("messages") != null)
            { %>
            <% List<HelpMessage> messages = (List<HelpMessage>) request.getAttribute("messages"); %>
            <% if (messages.isEmpty())
                { %>
            <p>No new messages.</p>
            <% } else
            { %>
            <% for (HelpMessage msg : messages)
                    {%>
            <div class="message">
                <p><strong>From: <%= msg.getUserName()%></strong></p>
                <p><%= msg.getMessage()%></p>
                <p class="timestamp"><%= msg.getSubmissionDate()%></p>
            </div>
            <% } %>
            <% } %>
            <% } else
        { %>
            <p>No messages available.</p>
            <% }%>
        </div>
    </body>
</html>
