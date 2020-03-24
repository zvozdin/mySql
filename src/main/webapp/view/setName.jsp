<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | ${command} | tableName</title>
    </head>
    <body>
        <c:set var="command" scope="session" value="${command}"/>

        <c:choose>
            <c:when test="${command == 'newDatabase'}">
                <form action="${command}" method="post">
                    Database Name:<br>
                    <input type="text" name="${command}"><br>
                    <br>
                    <input type="submit" value="${command}">
                </form>
            </c:when>
            <c:otherwise>
                <form action="edit.jsp" method="get">
                    Table Name:<br>
                    <input type="text" name="${command}"><br>
                    <br>
                    <input type="submit" value="${command}">
                </form>
            </c:otherwise>
        </c:choose>

        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>