<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            width: 600px;
            border-collapse: collapse;
        }

        TD, TH {
            padding: 3px;
            border: 1px solid
        }
    </style>
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
        <tr style="background-color:${meal.excess ? 'FF2200' : '22FF00'}">
            <td align="center"><javatime:format pattern="dd-MM-yyyy HH:mm" value="${meal.dateTime}"/></td>
            <td>${meal.description}</td>
            <td align="center">${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=new">Add Meal</a></p>
<br/>
<p><a href="meals?action=initDB">Initiate database</a></p>
</body>
</html>