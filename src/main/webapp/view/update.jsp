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
            Set column:
            <select name="setColumn">
                <c:forEach items="${tables}" var="setColumn">
                    <option value="${fn:trim(setColumn)}">${setColumn}</option>
                </c:forEach>
            </select>
            = <input type="text" name="setValue" />
            <br><br>
            Where column:
            <select name="whereColumn">
                <c:forEach items="${tables}" var="whereColumn">
                    <option value="${fn:trim(whereColumn)}">${whereColumn}</option>
                </c:forEach>
            </select>
            = <input type="text" name="whereValue" />
            <br><br>
            <input type="submit" value="${command}" />
        </form>
        <a href="/mySql/menu" >menu</a> <a href="/mySql/help" >help</a>
    </body>
</html>