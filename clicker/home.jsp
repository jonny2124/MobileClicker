<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
    <div class="navbar">
        <a href="home">Home</a>
        <a href="questionBank">Create New Question Bank</a> 
        <a href="statistic">Statistics</a>
        <a href="logout">Logout</a>
    </div>
    <div class="container">
        <h1>Welcome to the Teacher Dashboard</h1>
        <p>Use the navigation bar to manage your classes and view student responses.</p>
        <c:if test="${not empty questionBanks}">
            <c:forEach var="questionBank" items="${questionBanks}">
                <a href="questionDisplay?bankId=${questionBank.bankId}">Start Quiz - ${questionBank.title}</a>
            </c:forEach>
        </c:if>
        <c:if test="${empty questionBanks}">
            <p>There are no question banks available.</p>
        </c:if>
    </div>
</body>
</html>
