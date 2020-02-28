<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql | help</title>
    </head>
    <body>
        Existing commands:<br>
        	<a href="help">help</a><br>
        		to display a list of commands<br>
        	<a href="connect">connect</a>|database|user|password<br>
        		to connect to the database<br>
        	<a href="newDatabase">newDatabase</a><br>
        		to create a new database<br>
        	<a href="dropDatabase">dropDatabase</a><br>
        		to delete the database<br>
        	<a href="tables">tables</a><br>
        		to display a list of tables<br>
        	<a href="newTable">newTable</a><br>
        		to create a new table<br>
        	<a href="dropTable">dropTable</a><br>
        		to delete the table<br>
        	<a href="find">find</a><br>
        		to display content from the 'tableName'<br>
        	<a href="insert">insert</a><br>
        		to record content to the 'tableName'<br>
        	<a href="update">update</a><br>
        		to update the content in the 'tableName'<br>
        			set column1 = value1 where column2 = value2<br>
        	delete<br>
        		to delete content where column = value<br>
        	<a href="clear">clear</a><br>
        		to delete content from the 'tableName'<br><br>

    <a href="menu">menu</a>
    </body>
</html>