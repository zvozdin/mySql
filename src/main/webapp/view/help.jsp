<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | help</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/help.js" ></script>
    </head>
    <body>
        <div id="help_container">
            Existing commands:<br>
            <div id="commands">
                <div id="loading">Loading...</div>
            </div>
        </div>

    <a href="menu">menu</a>
    </body>
</html>