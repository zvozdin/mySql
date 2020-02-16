<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | connect</title>
    </head>
    <body>
        <form action="connect" method="post">

            Database Name:<br>
            <input type="text" name="database"><br>
            User Name:<br>
            <input type="text" name="user"><br>
            Password:<br>
            <input type="password" name="password"><br><br>

            <input type="submit" value="Connect">
        </form>
    </body>
</html>