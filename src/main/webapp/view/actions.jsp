<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | table | result</title>
    </head>
    <body>
        <table border="1">
            <c:forEach items="${actions}" var="userAction" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>
                    <td>${userAction.userName}</td>
                    <td>${userAction.dbName}</td>
                    <td>${userAction.action}</td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="/mySql/menu">menu</a> <a href="/mySql/help">help</a>
    </body>
</html>