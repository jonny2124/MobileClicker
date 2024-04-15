<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Response Overview</title>
    <style>
        /* Add your CSS styles here */
        body { font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        /* ... additional styles ... */
    </style>
</head>
<body>
    <h2>Responses Overview</h2>
    <!-- Table to display questions and responses -->
    <table>
        <thead>
            <tr>
                <th>Question No</th>
                <th>Choice</th>
                <th>Response Count</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${responses}" var="response">
                <tr>
                    <td><c:out value="${response.questionNo}" /></td>
                    <td><c:out value="${response.choice}" /></td>
                    <td><c:out value="${response.count}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Possible form to post new questions -->
    <!-- ... -->
</body>
</html>
