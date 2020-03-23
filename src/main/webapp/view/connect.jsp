<%@ taglib prefix="form" uri = "http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>mysql | connect</title>
    </head>
    <body>
        <form:form method ="POST" action="connect" modelAttribute="connection" >
            <form:hidden path="page" id="fromPage" />

            Database Name:<br>
            <form:input path="database" /><br>
            User Name:<br>
            <form:input path="user" /><br>
            Password:<br>
            <form:password path="password" /><br><br>

            <input type="submit" value="connect" />
        </form:form>
        <a href="menu">menu</a> <a href="help">help</a>
    </body>
</html>