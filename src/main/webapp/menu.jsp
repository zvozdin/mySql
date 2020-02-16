<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | menu</title>
    </head>
    <body>
        <c:forEach items="${commands}" var="command">
            <a href="${command}">${command}</a><br>
        </c:forEach>
    </body>
</html>