<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <td>${meal.id}</td>
            <td>${fn:replace(meal.dateTime,'T',' ')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">
                <button>Edit</button>
            </a></td>
            <td><a href="meals?action=delete&id=${meal.id}">
                <button>Delete</button>
            </a></td>
        </tr>
    </c:forEach>
</table>
${result}
<h3><a href="?action=addmeal">Add meal</a></h3>
<h2><a href="index.html">Home</a></h2>
</body>
</html>
