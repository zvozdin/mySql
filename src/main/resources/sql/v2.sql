insert into database_connection (user_name, db_name)
  select distinct user_name, db_name from user_actions;


update user_actions dest, database_connection src
set dest.database_connection_id = src.id
where dest.user_name = src.user_name and dest.db_name = src.db_name;