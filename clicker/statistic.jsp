<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Statistics</title>
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
        <h2 class="chart-title">Student Response Statistics</h2>
        <div class="bar-container">
            <c:forEach items="${statisticMap}" var="entry">
                <div class="bar" style="width: ${entry.value};">
                    <span class="label">Question ${entry.key}</span>
                    <span class="count">(${entry.value})</span>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
