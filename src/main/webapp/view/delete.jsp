<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>mysql | ${head}</title>
    </head>
    <body>
        Table: <a href="/mySql/tables/${head}" >${head}</a>
        <br><br>
        <form action="/mySql/${command}" method="post">
            <input type="hidden" name="name" value="${head}">
            Delete record where column:
            <select name="deleteColumn">
                <c:forEach items="${tables}" var="column">
                    <option value="${fn:trim(column)}">${column}</option>
                </c:forEach>
            </select>
            = <input type="text" name="deleteValue" />
            <br><br>
            <input type="submit" value="${command}" />
        </form>
        <a href="/mySql/menu" >menu</a> <a href="/mySql/help" >help</a>
    </body>
</html>