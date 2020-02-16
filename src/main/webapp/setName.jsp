<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | ${command} | tableName</title>
    </head>
    <body>
        <form action="${command}" method="post">
            Name:<br>
            <input type="text" name="${command}"><br>
            <br>
            <input type="submit" value="${command}">
        </form>
        <br>
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>