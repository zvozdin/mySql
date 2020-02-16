<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | find | tableName</title>
    </head>
    <body>
        <form action="find" method="post">
            Table Name:<br>
            <input type="text" name="table"><br>

            <input type="submit" value="Find">
        </form>
        <br>
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>