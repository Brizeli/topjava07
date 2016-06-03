<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h1>Meals list</h1>
<table border="1" style="border-collapse: collapse;">
    <c:forEach items="${mealsList}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed==true}">
                <c:set var="color" value="red"/>
            </c:when>
            <c:otherwise>
                <c:set var="color" value="green"/>
            </c:otherwise>
        </c:choose>
        <tr style="color: ${color};">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
<h2><a href="index.html">Home</a></h2>
</body>
</html>
