<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <form action="register" method="post" class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Register</button>
        </form>
        <a href="login.jsp" class="btn-link">Already have an account? Login here.</a>
    </div>
</body>
</html>
