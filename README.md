# mySql
![](https://travis-ci.com/zvozdin/mySql.svg?branch=master)

!!! Для выполнения всех тестов необходимо ввести имя своей базы данных, имя юзера и пароль в 

src\test\java\testSettingsToConnectDB

Проект MySQL

Техническое задание на проект SQLCmd

написание приложения на языке Java, 
реализующего функционал консольного клиента для работы с конкретной базой данных. 
Проект должен отвечать следующим требованиям:

Приложение должно использовать паттерн MVC
Приложение должно иметь консольный интерфейс взаимодействия с пользователем, 
то есть реализован ввод команд с клавиатуры и вывод результатов на экран.
Должны быть реализованы следующие консольные команды:
сonnect        
Команда для подключения к соответствующей БД
Формат команды: connect | database | username | password
где: database - имя БД
username -  имя пользователя БД
password - пароль пользователя БД
Формат вывода: текстовое сообщение с результатом выполнения операции
tables
Команда выводит список всех таблиц
Формат: tables (без параметров)
Формат вывода:
в любом удобном формате
например [table1, table2, table3]
clear
Команда очищает содержимое указанной (всей) таблицы
Формат: clear | tableName
где tableName - имя очищаемой таблицы
Формат вывода: текстовое сообщение с результатом выполнения операции
drop
Команда удаляет заданную таблицу
Формат: drop | tableName
где tableName - имя удаляемой таблицы
Формат вывода: текстовое сообщение с результатом выполнения операции
create
Команда создает новую таблицу с заданными полями
Формат: create | tableName | column1 | column2 | ... | columnN
где: tableName - имя таблицы
column1 - имя первого столбца записи
column2 - имя второго столбца записи
columnN - имя n-го столбца записи
Формат вывода: текстовое сообщение с результатом выполнения операции
find 
Команда для получения содержимого указанной таблицы
Формат: find | tableName
где tableName - имя таблицы
Формат вывода: табличка в консольном формате
+--------+---------+------------------+
+  col1  +  col2   +       col3       +
+--------+---------+------------------+
+  123   +  stiven +     pupkin       +
+  345   +  eva    +     pupkina      +
+--------+---------+------------------+
insert
Команда для вставки одной строки в заданную таблицу
Формат: insert | tableName | column1 | value1 | column2 | value2 | ... | columnN | valueN
где: tableName - имя таблицы
column1 - имя первого столбца записи
value1 - значение первого столбца записи
column2 - имя второго столбца записи
value2 - значение второго столбца записи
columnN - имя n-го столбца записи
valueN - значение n-го столбца записи
Формат вывода: текстовое сообщение с результатом выполнения операции
update
Команда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1
Формат: update | tableName | column1 | value1 | column2 | value2
где: tableName - имя таблицы
column1 - имя столбца записи которое проверяется
value1 - значение которому должен соответствовать столбец column1 для обновляемой записи
column2 - имя обновляемого столбца записи
value2 - значение обновляемого столбца записи
columnN - имя n-го обновляемого столбца записи
valueN - значение n-го обновляемого столбца записи
Формат вывода: табличный, как при find со старыми значениями обновленных записей.
delete
Команда удаляет одну или несколько записей для которых соблюдается условие column = value
Формат: delete | tableName | column | value
где: tableName - имя таблицы
Column - имя столбца записи которое проверяется
value - значение которому должен соответствовать столбец column1 для удаляемой записи
Формат вывода: табличный, как при find со старыми значениями удаляемых записей.
help 
Команда выводит в консоль список всех доступных команд
Формат: help (без параметров)
Формат вывода: текст, описания команд с любым форматированием
exit 
Команда для отключения от БД и выход из приложения
Формат: exit (без параметров)
Формат вывода: текстовое сообщение с результатом выполнения операции
