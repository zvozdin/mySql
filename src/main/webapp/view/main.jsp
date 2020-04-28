<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | tables</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/main.js" ></script>
        <script type="text/javascript">
            $(window).load(function(){
                init('${ctx}');
            });
        </script>
    </head>
    <body>
        <div id="loading" style="display:none;" >Loading...</div>
        <%@ include file="help.jsp" %>
        <%@ include file="menu.jsp" %>
        <%@ include file="tables.jsp" %>
        <%@ include file="table.jsp" %>

        <a href="#/menu">menu</a>
    </body>
</html>