<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Question Display</title>
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
        <c:choose>
            <c:when test="${not empty currentQuestion}">
                <div class="question-bank-list">
                    <h2>Question ${currentQuestion.questionId-1}</h2>
                    <p>${currentQuestion.questionText}</p>
                    <p>A. ${currentQuestion.optionA}</p>
                    <p>B. ${currentQuestion.optionB}</p>
                    <p>C. ${currentQuestion.optionC}</p>
                    <p>D. ${currentQuestion.optionD}</p>
                </div>
                <form action="questionDisplay" method="post" class="form-group">
                    <input type="hidden" name="bankId" value="${currentQuestion.bankId}">
                    <input type="hidden" name="currentQuestionId" value="${currentQuestion.questionId}">
                    <button type="submit" class="btn-next">Next</button>
                </form>
            </c:when>
            <c:otherwise>
                <div class="end-quiz-message">
                    <p>Quiz over! Good job!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
