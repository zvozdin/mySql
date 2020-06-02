# *Training project* mySql
***
![Imgur](https://i.imgur.com/hJ7zUG4.gifv)
![Build Status](https://travis-ci.com/zvozdin/mySql.svg?branch=master)
***

### **Technical task**

Java web 2.0 application to work with the database 

   *Requirements*:
* use MVC pattern; 
* have a web 2.0 interface for user interaction;
* have to register all user actions in the database;
* the following commands should be implemented:  
  * **сonnect**  
    * command to connect to the suit database;
    * result: return to the page from which user has started; 
    
  * **tables**  
    * command to list all the tables;
    * result: list of tables as links;
      
  * **clear**
    * command to clear the data table;  
    * result: operation result text message;   
    
  * **dropTable**
    * command to delete the table; 
    * result: operation result text message;  
    
  * **newTable**
    * command to create a new table;
    * result: operation result text message;  
    
  * **insert**
    * command to insert one row into the table;    
    * result: operation result text message;  
    
  * **update**
    * command to update the record by setting   
      column1 = value1, where column2 = value2  
    * result: table;  
    
  * **delete**
    * command to delete a record where column = value;  
    * result: table;  
    
  * **help**
    * command to display all available commands list;
    * result: list of commands as links;  
    
  * **actions**
    * command to show all user actions;
    * result: table of actions in the database by the user.
    
***!!! To perform all tests, you have to enter   
your database name, user name and password in    
src/main/resources/db.properties***
