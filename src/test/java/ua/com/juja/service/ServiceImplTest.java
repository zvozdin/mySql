package ua.com.juja.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.juja.model.DatabaseConnectionsRepository;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.UserActionsRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SpringConfig.class)
//@WebAppConfiguration
public class ServiceImplTest {

    @Mock
    private UserActionsRepository userActions;

    @Mock
    private DatabaseConnectionsRepository databaseConnections;

    @Autowired
    DatabaseManager manager;

    @InjectMocks
    private ServiceImpl service;

//    @Before
//    public void setup() {
//        initMocks(this);
//    }

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

}