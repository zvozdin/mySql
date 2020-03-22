<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | menu</title>
    </head>
    <body>
        <table border="1">
            <tr><td></td> <td>Tables</td></tr>
            <c:forEach items="${tables}" var="table" varStatus="loop">
                <tr><td>${loop.count}</td> <td>${table}</td></tr>
            </c:forEach>
        </table>
        <br>
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>