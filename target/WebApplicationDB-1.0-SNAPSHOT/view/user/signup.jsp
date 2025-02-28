<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - WebApplicationDB</title>
    <link rel="stylesheet" href="/WebApplicationDB/css/login.css">
</head>
<body>
    <div class="signup-container">
        <h1>WebApplicationDB</h1>
        <div class="subtitle">Create a new account</div>
        
        <c:if test="${not empty errors.signup}">
            <div class="error">${errors.signup}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/signup" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="${formData.username}" autofocus>
                <c:if test="${not empty errors.username}">
                    <div class="error">${errors.username}</div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password">
                <c:if test="${not empty errors.password}">
                    <div class="error">${errors.password}</div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword">
                <c:if test="${not empty errors.confirmPassword}">
                    <div class="error">${errors.confirmPassword}</div>
                </c:if>
            </div>
            
            <div class="button-container">
                <a href="${pageContext.request.contextPath}/login" class="secondary-link">Back to login</a>
                <button type="submit" class="primary-button">Create account</button>
            </div>
        </form>
    </div>
</body>
</html>