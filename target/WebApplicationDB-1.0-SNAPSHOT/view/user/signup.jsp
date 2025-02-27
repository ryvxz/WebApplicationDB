<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - WebApplicationDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .signup-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 350px;
            max-width: 90%;
        }
        h1 {
            color: #4285f4;
            text-align: center;
            margin-bottom: 20px;
            font-weight: normal;
        }
        .subtitle {
            color: #5f6368;
            text-align: center;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #5f6368;
            font-size: 14px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #dadce0;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        .error {
            color: #d93025;
            font-size: 12px;
            margin-top: 3px;
        }
        .button-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 30px;
        }
        .primary-button {
            background-color: #4285f4;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .primary-button:hover {
            background-color: #357ae8;
        }
        .secondary-link {
            color: #4285f4;
            text-decoration: none;
            font-size: 14px;
        }
        .secondary-link:hover {
            text-decoration: underline;
        }
    </style>
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