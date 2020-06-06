<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add/edit Meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    <c:if test="${meal != null}">
        Id : <input readonly
            type="text" name="mealId"
            value="${meal.id}"/>
    </c:if>
    <br/>
    Date and time : <input
        type="datetime-local" name="dateTime"
        value="<javatime:format pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="${meal.calories}"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>