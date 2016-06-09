<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal{
            color: green;
        }
        .exceed{
            color: red;
        }
    </style>
</head>
<body>
<h1>Meals list</h1>
<table border="1" style="border-collapse: collapse;">
    <c:forEach items="${mealsList}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMealWithExceed" scope="page"/>
        <tr class="${meal.exceed?'exceed':'normal'}">
            <td>${meal.id}</td>
            <td><%=TimeUtil.toString(meal.getDateTime())%></td>
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
