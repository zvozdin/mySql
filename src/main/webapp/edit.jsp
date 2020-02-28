<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>mysql | ${command} | tableName</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${command == 'newTable'}">
                <%
                    String tableName = request.getParameter("newTable");
                    request.setAttribute("tableName", tableName);
                %>
                <form action="${command}" method="post">
                    <input type="hidden" name="table" value="${tableName}">
                    Columns Names separated by '|'<br>
                    column1|column2|...|columnN :<br>
                    <input type="text" name="columns"><br><br>
                    <input type="submit" value="${command}">
                </form>
            </c:when>
            <c:when test="${command == 'insert'}">
                <%
                    String tableName = request.getParameter("insert");
                    request.setAttribute("tableName", tableName);
                %>
                <form action="${command}" method="post">
                    <input type="hidden" name="table" value="${tableName}">
                    Columns with Values to insert separated by '|'<br>
                    column1|value1|column2|value2|...|columnN|valueN :<br>
                    <input type="text" name="columns"><br><br>
                    <input type="submit" value="${command}">
                </form>
            </c:when>
            <c:when test="${command == 'update'}">
                <%
                    String tableName = request.getParameter("update");
                    request.setAttribute("tableName", tableName);
                %>
                <form action="${command}" method="post">
                    <input type="hidden" name="table" value="${tableName}">
                    Columns with Values to update separated by '|'<br>
                    column1|value1|column2|value2| :<br>
                    <input type="text" name="columns"><br><br>
                    <input type="submit" value="${command}">
                    </form>
            </c:when>
                    <c:otherwise>
                        <form action="edit.jsp" method="get">
                            Table Name:<br>
                            <input type="text" scope="session"  name="${command}"><br>
                            <br>
                            <input type="submit" value="${command}">
                        </form>
                    </c:otherwise>
                </c:choose>

        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>