<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | ${command} | tableName</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${command == 'newDatabase'}">
                <form action="${command}/${name}" method="get">
                    Database Name:<br>
                    <input type="text" name="name"><br>
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