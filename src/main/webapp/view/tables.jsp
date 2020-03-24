<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <title>mysql | tables</title>
    </head>
    <body>
        ${report}
        <table border="1">
            <tr>
                <td></td> <td>Tables</td>
            </tr>
            <c:forEach items="${tables}" var="table" varStatus="loop">
                <tr>
                    <td>${loop.count}</td><td><a href="${command}/${fn:trim(table)}">${table}</a></td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="/mySql/menu">menu</a> <a href="/mySql/help">help</a>
    </body>
</html>