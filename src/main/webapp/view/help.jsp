<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <head>
        <title>mysql | help</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js" ></script>
        <script type="text/javascript" src="${ctx}/resources/js/help.js" ></script>
    </head>
    <body>
        <div id="help_container">
            Existing commands:
            <div>
                <div id="loading">Loading...</div>
                <dl id="commands">
                    <script id="descriptionRow" type="text/x-jquery-tmpl">
                        <li class="leftnavlink"><a href="${ctx}/{{= command}}">{{= command}}</a></li>
                        <dd>{{= description}}</dd>
                    </script>
                </dl>
            </div>
        </div>
        <a href="menu">menu</a>
    </body>
</html>