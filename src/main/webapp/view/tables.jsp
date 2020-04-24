<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | tables</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/tables.js" ></script>
    </head>
    <body>
        ${report}
        <div id="tables">
            <div id="loading">Loading...</div>
                <table border="1" class="containers">
                    <tr>
                        <td>Tables</td>
                    </tr>
                    <script id="temp">
                        <tr>
                            <td><a href="tables/{{= $data}}" >{{= $data}}</a></td>
                        </tr>
                    </script>
                </table>
        </div>
        <br>
        <a href="${ctx}/menu">menu</a> <a href="${ctx}/help">help</a>
    </body>
</html>