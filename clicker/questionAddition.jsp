<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Question</title>
    <link href="styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Add Question to Bank</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="questionManagement" method="post">
        <input type="hidden" name="bankId" value="<%= request.getAttribute("bankId") %>">
        <div class="form-group">
            <label for="questionText">Question:</label>
            <textarea id="questionText" name="questionText" required></textarea>
        </div>
        <div class="form-group">
            <label for="optionA">Option A:</label>
            <input type="text" id="optionA" name="optionA" required>
        </div>
        <div class="form-group">
            <label for="optionB">Option B:</label>
            <input type="text" id="optionB" name="optionB" required>
        </div>
        <div class="form-group">
            <label for="optionC">Option C:</label>
            <input type="text" id="optionC" name="optionC" required>
        </div>
        <div class="form-group">
            <label for="optionD">Option D:</label>
            <input type="text" id="optionD" name="optionD" required>
        </div>
        <div class="form-group">
            <label for="correctOption">Correct Option:</label>
            <select id="correctOption" name="correctOption" required>
                <option value="A">Option A</option>
                <option value="B">Option B</option>
                <option value="C">Option C</option>
                <option value="D">Option D</option>
            </select>
        </div>
        <button type="submit">Add Question</button>
        <a href="home"><button type="button">Finish</button></a>
    </form>
</div>
</body>
</html>
