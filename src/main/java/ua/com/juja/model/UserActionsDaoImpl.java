package ua.com.juja.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserActionsDaoImpl implements UserActionsDao {

    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void log(String userName, String dbName, String action) {
        template.update("insert into user_actions (user_name, db_name, action) values (?, ?, ?)",
                userName, dbName, action);
    }

    @Override
    public List<UserAction> getAllFor(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("'userName' can't be null");
        }

        return template.query("select * from user_actions where user_name = ?", new Object[]{userName},
                new RowMapper<UserAction>() {
                    @Override
                    public UserAction mapRow(ResultSet rs, int rowNum) throws SQLException {
                        UserAction result = new UserAction();
                        result.setId(rs.getInt("id"));
                        result.setUserName(rs.getString("user_name"));
                        result.setDbName(rs.getString("db_name"));
                        result.setAction(rs.getString("action"));
                        return result;
                    }
                });
    }
}