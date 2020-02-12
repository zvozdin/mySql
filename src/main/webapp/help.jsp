<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>mysql</title>
    </head>
    <body>
        Existing commands:<br>
        	help<br>
        		to display a list of commands<br>
        	connect|databaseName|user|password<br>
        		to connect to the database<br>
        	newDatabase|databaseName<br>
        		to create a new database<br>
        	dropDatabase|databaseName<br>
        		to delete the database<br>
        	list<br>
        		to display a list of tables<br>
        	create|tableName|column1|column2|...|columnN<br>
        		to create a new table<br>
        	drop|tableName<br>
        		to delete the table<br>
        	find|tableName<br>
        		to retrieve content from the 'tableName'<br>
        	insert|tableName|column1|value1|column2|value2|...|columnN|valueN<br>
        		to record content to the 'tableName'<br>
        	update|tableName|column1|value1|column2|value2<br>
        		to update the content in the 'tableName'<br>
        			set column1 = value1 where column2 = value2<br>
        	delete|tableName|column|value<br>
        		to delete content where column = value<br>
        	clear|tableName<br>
        		to delete content from the 'tableName'<br>
        	exit<br>
        		to exit from the program<br>
    <a href="menu">Menu</a>
    </body>
</html>