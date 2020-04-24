<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | table | result</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/table.js" ></script>
        <script type="text/javascript">
            $(window).load(function(){
                initTable('${ctx}');
            });
        </script>
    </head>
    <body>
        ${report}
        <div id="table">
            <div id="loading">Loading...</div>
            <table border="1" class="container">
                <script id="temp">
                    <tr>
                        {{each $data}}
                            <td>
                                {{= this}}
                            </td>
                        {{/each}}
                    </tr>
                </script>
            </table>
        </div>
        <br>
        <a href="${ctx}/menu">menu</a> <a href="${ctx}/help">help</a>
    </body>
</html>