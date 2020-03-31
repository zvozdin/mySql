<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>mysql | newTable | ${name}</title>
    </head>
    <body>
        <form action="newTable" method="post" >
            <input type="hidden" name="name" value="${name}">
            <c:forEach begin="1" end="${count}" varStatus="loop">
                Column #${loop.count}<br/>
                <input type="text" name="column${loop.count}" /><br>
            </c:forEach>
            <br>
            <input type="submit" value="newTable" />
        </form>
        <br>
        <a href="/mySql/menu">menu</a> <a href="/mySql/help">help</a>
    </body>
</html>