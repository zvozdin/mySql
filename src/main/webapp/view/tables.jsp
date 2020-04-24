<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | ${head}</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/tables.js" ></script>
    </head>
    <body>
        ${report}
        <div id="tables">
            <div id="loading">Loading...</div>
                <dl class="container">
                    <table id="containerTable" border="1" >
                        <tr>
                            <td>Tables</td>
                        </tr>
                    </table>
                    <row-template style="display:none;" >
                        <tr>
                            <td><a href="tables/{{= $data}}" >{{= $data}}</a></td>
                            <br>
                        </tr>
                    </row-template>
                </dl>
            </div>
        </div>
        <a href="/mySql/menu">menu</a> <a href="/mySql/help">help</a>
    </body>
</html>