<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>mysql | ${head}</title>
    </head>
    <body>
        Table: <a href="/mySql/tables/${head}" >${head}</a>
        <form action="/mySql/${command}" method="post" >
            <input type="hidden" name="name" value="${head}">
            <table border="1" >
                <tr>
                    <c:forEach items="${tables}" var="name" >
                        <td>${name}</td>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach items="${tables}" var="name" >
                        <td><input type="text" name="${name}" /></td>
                    </c:forEach>
                </tr>
            </table>
            <br>
            <input type="submit" value="${command}" />
        </form>
        <a href="/mySql/menu" >menu</a> <a href="/mySql/help" >help</a>
    </body>
</html>