<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'exceeded-meals' : 'normal-meals'}">
            <td align="center"><javatime:format pattern="yyyy-MM-dd HH:mm" value="${meal.dateTime}"/></td>
            <td>${meal.description}</td>
            <td align="center">${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=new">Add Meal</a></p>
</body>
</html>