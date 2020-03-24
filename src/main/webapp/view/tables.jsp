<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <title>mysql | tables</title>
    </head>
    <body>
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
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>