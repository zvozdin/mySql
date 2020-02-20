<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | table | result</title>
    </head>
    <body>
        ${report}
        <table border="1">
            <c:forEach items="${rows}" var="row">
                <tr>
                    <c:forEach items="${row}" var="element">
                        <td>
                            ${element}
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>