<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<form action="meals" method="post">
    <fieldset style="width: 300px;">
        <input type="text" name="id" value="${meal.id}" hidden/>
        Date and time<br>
        <input type="datetime-local" name="datetime" value="${meal.dateTime}" required/><br>
        Description<br>
        <input type="text" name="desc" value="${meal.description}"><br>
        Calories<br>
        <input type="number" name="cal" value="${meal.calories}" required>
        <input style="margin-left: 50px" type="submit" value="${addupd}">
    </fieldset>
</form>
${result}<br>
<a href="meals">Back</a>
<h2><a href="index.html">Home</a></h2>
</body>
</html>