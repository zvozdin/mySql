<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>mysql | ${command} | setName</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${command == 'newDatabase'}" >
                <form action="${command}/${name}" method="get" >
                    Database Name:<br>
                    <input type="text" name="name" /><br>
                    <br>
                    <input type="submit" value="${command}" />
                </form>
            </c:when>
            <c:otherwise>
                <form action="newTable" method="get" >
                    Table Name:<br>
                    <input type="text" name="name" /><br>
                    Columns count:<br>
                    <input type="number" min="1" name="count" /><br>
                    <br>
                    <input type="submit" value="setColumnNames" />
                </form>
            </c:otherwise>
        </c:choose>
        <a href="menu" >menu</a> <a href="help" >help</a>
    </body>
</html>