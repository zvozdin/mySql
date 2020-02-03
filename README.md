# *Training project* mySql
***

![Build Status](https://travis-ci.com/zvozdin/mySql.svg?branch=master)
***

### **Technical task**

Java application implements console client to work with the database 

   *Requirements*:
* use MVC pattern; 
* have a console interface for user interaction;  
* the following console commands should be implemented:  
  * **сonnect**  
    * command to connect to the suit database;  
    * command format: connect|database|user|password, where  
          - database - database name,    
          - user -  user name,    
          - password - user password;
    * output format: operation result text message; 
    
  * **tables**  
    * command to list all the tables;    
    * command format: list;
    * output format: [table1, table2, table3]  
      
  * **clear**
    * command to clear the data table;  
    * command format: clear|table, where      
          - table - table name;  
    * output format: operation result text message;   
    
  * **drop**
    * command to delete the table;
    * command format: drop|table, where  
          - table - table name;  
    * output format: operation result text message;  
    
  * **create**
    * command to create a new table;
    * command format: create|table|column1|column2| ... |columnN, where
          - table - table name,  
          - column1 - first column name,     
          - column2 - second column name,    
          - columnN - N-th column name;  
    * output format: operation result text message;  
    
  * **find**
    * command to get the data table;        
    * command format: find|table, where  
          - table - table name;     
    * output format: table in format:    
    
             +---------+---------+---------+  
             | column1 | column2 | columnN |  
             +---------+---------+---------+  
             | value1  | value2  | valueN  |  
             +---------+---------+---------+  
             
  * **insert**
    * command to insert one row into the table;    
    * command format:  
      insert|table|column1|value1|column2|value2| ... |columnN|valueN, where    
          - table - table name,  
          - column1 - first column name,       
          - value1 - first column value,  
          - column2 - second column name,  
          - value2 - second column value,   
          - columnN - N-th column name,  
          - valueN - N-th column value;  
    * output format: operation result text message;  
    
  * **update**
    * command to update the record by setting   
      column1 = value1, where column2 = value2  
    * command format: update|table|column1|value1|column2|value2, where  
          - table - table name,  
          - column1 - column name to update,    
          - value1 - column value to update,      
          - column2 - column name to be checked,     
          - value2 - column2's value that must match to update the record;  
    * output format: table;  
    
  * **delete**
    * command to delete one or more records where column = value;  
    * command format: delete|table|column|value, where  
          - table - table name,  
          - сolumn - column name to delete,  
          - value - column's value that must match to delete the record;  
    * output format: table;  
    
  * **help**
    * command to display all available commands list;  
    * command format: help;  
    * output format: commands description text;  
    
  * **exit**
    * command to disconnect the database and close the application;  
    * output format: exit;  
    * output format: operation result text message.
    
***!!! To perform all tests, you must enter   
your database name, user name and password in    
src/main/resources/db.properties***