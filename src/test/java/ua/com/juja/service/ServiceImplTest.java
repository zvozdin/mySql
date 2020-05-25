package ua.com.juja.service;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.juja.model.DatabaseConnectionsRepository;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.UserActionsRepository;
import ua.com.juja.model.entity.DatabaseConnection;
import ua.com.juja.model.entity.UserAction;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceImplTest {

    @Mock
    private UserActionsRepository userActions;

    @Mock
    private DatabaseConnectionsRepository databaseConnections;

    @Autowired
    private DatabaseManager manager;

    @Mock
    private DatabaseConnection connection;

    @InjectMocks
    private ServiceImpl service;

    @Test
    public void testAddService() {
        assertNotNull(service);
    }

    @Test
    public void testGetCommands() {
        assertEquals("[" +
                "help, " +
                "connect, " +
                "newDatabase, " +
                "dropDatabase, " +
                "tables, " +
                "newTable, " +
                "dropTable, " +
                "insert, " +
                "update, " +
                "delete, " +
                "clear, " +
                "actions]", service.getCommands().toString());
    }

    @Test
    public void testGetCommandsDescription() {
        assertEquals("[" +
                "connect=to connect to the database, " +
                "newDatabase=to create a new database, " +
                "dropDatabase=to delete the database, " +
                "tables=to display a list of tables, " +
                "newTable=to create a new table, " +
                "dropTable=to delete the table, " +
                "insert=to record content to the 'tableName', " +
                "update=to update the content in the 'tableName' set column1 = value1 where column2 = value2, " +
                "delete=to delete content where column = value, " +
                "clear=to delete content from the 'tableName']", service.getCommandsDescription().toString());
    }

    @Test
    public void testGetAllFor() {
        // given
        List<DatabaseConnection> connections = new LinkedList<>();
        connections.add(connection);

        List<UserAction> actions = new LinkedList<>();
        actions.add(new UserAction("CONNECT", connections.get(0)));
        actions.add(new UserAction("TABLES", connections.get(0)));

        ObjectId id = new ObjectId("5eb80b1ac866006a990b79f4");

        // when
        when(connection.getId()).thenReturn("5eb80b1ac866006a990b79f4");
        when(connection.getUserName()).thenReturn("user");
        when(connection.getDatabase()).thenReturn("database");
        when(databaseConnections.findByUserName("user")).thenReturn(connections);
        when(userActions.findByDatabaseConnectionId(id))
                .thenReturn(actions);

        // then
        assertEquals("[" +
                "userName = user, database = database, action = CONNECT, " +
                "userName = user, database = database, action = TABLES]",
                service.getAllFor("user").toString());

        verify(databaseConnections).findByUserName("user");
        verify(userActions).findByDatabaseConnectionId(new ObjectId("5eb80b1ac866006a990b79f4"));
        verifyNoMoreInteractions(databaseConnections);
        verifyNoMoreInteractions(userActions);
    }

    @Test
    public void testConnect() {
        // given
        List<DatabaseConnection> connections = new LinkedList<>();
        connections.add(connection);

        List<UserAction> actions = new LinkedList<>();
        actions.add(new UserAction("CONNECT", connections.get(0)));
        actions.add(new UserAction("TABLES", connections.get(0)));


        // when
        when(connection.getId()).thenReturn("5eb80b1ac866006a990b79f4");
        when(connection.getUserName()).thenReturn("user");
        when(connection.getDatabase()).thenReturn("database");
        when(databaseConnections.findByUserName("user")).thenReturn(connections);
        when(userActions.findByDatabaseConnectionId(new ObjectId("5eb80b1ac866006a990b79f4")))
                .thenReturn(actions);

        // then
        assertEquals("[" +
                        "userName = user, database = database, action = CONNECT, " +
                        "userName = user, database = database, action = TABLES]",
                service.getAllFor("user").toString());
    }
}