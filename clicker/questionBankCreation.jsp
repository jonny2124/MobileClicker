<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Question Bank</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #0A0E1A;
            color: #FFFFFF;
        }
        .container {
            width: 60%;
            margin: 40px auto;
            background-color: #13294B;
            padding: 20px;
            border-radius: 10px;
        }
        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #1E90FF;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #105E9C;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Create Question Bank</h2>
    <form action="questionBank" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>
        
        <label for="subject">Subject:</label>
        <input type="text" id="subject" name="subject" required>
        
        <label for="difficulty">Difficulty (1-5):</label>
        <input type="number" id="difficulty" name="difficulty" min="1" max="5" required>
        
        <label for="colorScheme">Color Scheme:</label>
        <input type="text" id="colorScheme" name="colorScheme" placeholder="#FFFFFF" required>
        
        <label for="timePerQuestion">Time per Question (seconds):</label>
        <input type="number" id="timePerQuestion" name="timePerQuestion" min="1" required>
        
        <input type="submit" value="Create">
    </form>
</div>
</body>
</html>
