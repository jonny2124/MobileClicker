<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css" type="text/css"> <!-- Ensure this path is correct -->
    <style>
        /* Since we're linking to an external stylesheet, only page-specific styles are needed here */
        .login-container {
            background-color: #13294B;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
            margin-top: 100px; /* Adjust as needed */
        }

        .login-form {
            margin-top: 20px;
        }

        .login-form label {
            color: #DCE3EF;
        }

        .login-form input[type="email"],
        .login-form input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .login-form button {
            width: 100%;
            background-color: #1E90FF;
            color: white;
            padding: 14px 20px;
            margin: 24px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .login-form button:hover {
            background-color: #105E9C;
        }

        .register-link {
            text-align: center;
            margin-top: 20px;
        }

        .register-link a {
            color: #CDDC39;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-container">
        <h2>Login to Your Account</h2>
        <% String loginError = (String) request.getAttribute("loginError"); %>
        <% if(loginError != null) { %>
            <p style="color: #FF6347;"><%= loginError %></p>
        <% } %>
        <form action="login" method="post" class="login-form">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div>
                <button type="submit">Login</button>
            </div>
        </form>
    </div>
    <div class="register-link">
        <p>Don't have an account? <a href="register.jsp">Register here</a>.</p>
    </div>
</div>
</body>
</html>
