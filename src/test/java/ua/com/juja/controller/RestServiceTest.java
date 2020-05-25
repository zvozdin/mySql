package ua.com.juja.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.juja.config.SpringConfig;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.entity.Description;
import ua.com.juja.service.Service;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestServiceTest {

    private MockMvc mockMvc;

    @Mock
    private Service service;

    @Mock
    private DatabaseManager manager;

    @InjectMocks
    private RestService restService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = standaloneSetup(restService).build();
    }

    @Test
    public void test1_menu() throws Exception {
        // when
        when(service.getCommands()).thenReturn(Arrays.asList("test"));

        // then
        mockMvc.perform(get("/menu/content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$", is(Arrays.asList("test"))));
        verify(service).getCommands();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test2_help() throws Exception {
        // when
        when(service.getCommandsDescription()).thenReturn(Arrays.asList(
                new Description("commandA", "commandA description"),
                new Description("commandB", "commandB description")));

        // then
        mockMvc.perform(get("/help/content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].command", is("commandA")))
                .andExpect(jsonPath("$[0].description", is("commandA description")))
                .andExpect(jsonPath("$[1].command", is("commandB")))
                .andExpect(jsonPath("$[1].description", is("commandB description")));
        verify(service).getCommandsDescription();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test3_isConnected() throws Exception {
        // when
        when(manager.getUserName()).thenReturn("userName");

        // then
        mockMvc.perform(get("/connected").sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(content().string("userName"));
        verify(manager).getUserName();
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void test4_isNotConnected() throws Exception {
        // then
        mockMvc.perform(get("/connected"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verifyNoInteractions(manager);
    }

    @Test
    public void test5_connect() throws Exception {
        // when
        when(service.connect("database", "root", "root")).thenReturn(manager);

        // then
        mockMvc.perform(post("/connect")
                .param("database", "database")
                .param("user", "root")
                .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(service).connect("database", "root", "root");
        verify(service).saveUserAction("CONNECT", "root", "database");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test6_connectError() throws Exception {
        // when
        when(service.connect("database", "root", "root"))
                .thenThrow(new RuntimeException("Can't get connection for database: database, user: root"));

        // then
        mockMvc.perform(post("/connect")
                .param("database", "database")
                .param("user", "root")
                .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(content().string("Can't get connection for database: database, user: root"));

        verify(service).connect("database", "root", "root");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test7_tables() throws Exception {
        // given
        List<String> tables = new LinkedList();
        tables.add("table1");
        tables.add("table2");

        // when
        when(manager.getTables()).thenReturn(tables);

        // then
        mockMvc.perform(get("/tables/content").sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("table1")))
                .andExpect(jsonPath("$[1]", is("table2")))
                .andExpect(content().string("[\"table1\",\"table2\"]"));

        verify(manager).getTables();

        verify(service).saveUserAction("Tables", null, null);
        verifyNoMoreInteractions(manager);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test8_tableContent() throws Exception {
        // given
        List<List<String>> rows = new LinkedList<>();
        rows.add(new LinkedList<>(Arrays.asList("value1", "value2")));

        // when
        when(manager.getColumns("test")).thenReturn(new LinkedHashSet<>(Arrays.asList("column1", "column2")));
        when(manager.getRows("test")).thenReturn(rows);

        // then
        mockMvc.perform(get("/tables/{table}/content", "test")
                .sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(Arrays.asList("column1", "column2"))))
                .andExpect(jsonPath("$[1]", is(Arrays.asList("value1", "value2"))))
                .andExpect(content().string("[" +
                        "[\"column1\",\"column2\"]," +
                        "[\"value1\",\"value2\"]]"));

        verify(manager).getColumns("test");
        verify(manager).getRows("test");
        verify(service).saveUserAction("View Table(test)", null, null);
        verifyNoMoreInteractions(manager);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test9_newDatabase() throws Exception {
        // then
        mockMvc.perform(put("/newDatabase/{name}", "test")
                .sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(content().string("Database 'test' is created."));

        verify(manager).createDatabase("test");
        verify(service).saveUserAction("NewDatabase(test)", null, null);
        verifyNoMoreInteractions(manager);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test10_databasesContent() throws Exception {
        // when
        when(manager.getDatabases()).thenReturn(Arrays.asList("database1", "database2"));

        // then
        mockMvc.perform(get("/dropDatabase/content")
                .sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("database1")))
                .andExpect(jsonPath("$[1]", is("database2")))
                .andExpect(content().string("[\"database1\",\"database2\"]"));

        verify(manager).getDatabases();
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void test11_dropDatabase() throws Exception {
        // then
        mockMvc.perform(delete("/dropDatabase/{name}", "test")
                .sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(content().string("Database 'test' is deleted."));

        verify(manager).dropDatabase("test");
        verify(service).saveUserAction("DropDatabase(test)", null, null);
        verifyNoMoreInteractions(manager);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test12_newTable() throws Exception {
        // given
        LinkedMultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add("tableName", "test");
        queryMap.add("column1", "value1");
        queryMap.add("column2", "value2");

        // then
        mockMvc.perform(post("/newTable")
                .sessionAttr("manager", manager).queryParams(queryMap))
                .andExpect(status().isOk())
                .andExpect(content().string("Table 'test' is created."));

        queryMap.remove("tableName");
        verify(manager, atMostOnce()).createTable("test", new LinkedHashSet(queryMap.values()));
        verify(service).saveUserAction("NewTable(test)", null, null);
    }

    @Test
    public void test13_dropTable() throws Exception {
        // then
        mockMvc.perform(delete("/dropTable/{tableName}", "test")
                .sessionAttr("manager", manager))
                .andExpect(status().isOk())
                .andExpect(content().string("Table 'test' is deleted."));

        verify(manager).dropTable("test");
        verify(service).saveUserAction("DropTable(test)", null, null);
        verifyNoMoreInteractions(manager);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test14_getContent() throws Exception {
        // given
        List<String> crudOperationUrls = Arrays.asList(
                "/insert/{tableName}/content",
                "/update/{tableName}/content",
                "/delete/{tableName}/content");

        // when
        when(manager.getColumns("test"))
                .thenReturn(new LinkedHashSet<>(Arrays.asList("column1", "column2")));

        // then
        for (String crudOperationUrl : crudOperationUrls) {
            mockMvc.perform(get(crudOperationUrl, "test")
                    .sessionAttr("manager", manager))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*]", hasSize(2)))
                    .andExpect(jsonPath("$[0]", is("column1")))
                    .andExpect(jsonPath("$[1]", is("column2")))
                    .andExpect(content().string("[\"column1\",\"column2\"]"));

            verify(manager, atLeastOnce()).getColumns("test");
            verifyNoMoreInteractions(manager);
        }
    }

    @Test
    public void test15_insert() throws Exception {
        // given
        LinkedMultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add("tableName", "test");
        queryMap.add("column1", "value1");
        queryMap.add("column2", "value2");

        // then
        mockMvc.perform(post("/insert")
                .sessionAttr("manager", manager).queryParams(queryMap))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Record '{column1=value1, column2=value2}' is added."));

        queryMap.remove("tableName");
        verify(manager, atMostOnce()).insert("test", new LinkedHashMap<>(convertMultiToRegularMap(queryMap)));
        verify(service).saveUserAction("Insert into test", null, null);
    }

    @Test
    public void test16_update() throws Exception {
        // given
        LinkedMultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add("tableName", "test");
        queryMap.add("setColumn", "updatingColumn");
        queryMap.add("setValue", "updatingValue");
        queryMap.add("whereColumn", "conditionColumn");
        queryMap.add("whereValue", "conditionValue");

        Map<String, String> input = new LinkedHashMap<>(convertMultiToRegularMap(queryMap));

        Map<String, String> set = new LinkedHashMap<>();
        set.put(input.get("setColumn"), input.get("setValue"));

        Map<String, String> where = new LinkedHashMap<>();
        where.put(input.get("whereColumn"), input.get("whereValue"));

        // then
        mockMvc.perform(post("/update")
                .sessionAttr("manager", manager).queryParams(queryMap))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Record '{conditionColumn=conditionValue}' is updated."));

        queryMap.remove("tableName");
        verify(manager, atMostOnce()).update("test", set, where);
        verify(service).saveUserAction("Update in test", null, null);
    }

    @Test
    public void test17_delete() throws Exception {
        // given
        LinkedMultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        queryMap.add("tableName", "test");
        queryMap.add("deleteColumn", "deletingRecordColumn");
        queryMap.add("deleteValue", "deletingRecordValue");

        Map<String, String> input = new LinkedHashMap<>(convertMultiToRegularMap(queryMap));

        Map<String, String> delete = new LinkedHashMap<>();
        delete.put(input.get("deleteColumn"), input.get("deleteValue"));

        // then
        mockMvc.perform(post("/delete")
                .sessionAttr("manager", manager).queryParams(queryMap))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Record '{deletingRecordColumn=deletingRecordValue}' is deleted."));

        queryMap.remove("tableName");
        verify(manager, atMostOnce()).deleteRow("test", delete);
        verify(service).saveUserAction("DeleteRow in test", null, null);
    }

    private Map<String, String> convertMultiToRegularMap(MultiValueMap<String, String> m) {
        Map<String, String> map = new HashMap<>();
        if (m == null) {
            return map;
        }
        for (Map.Entry<String, List<String>> entry : m.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String s : entry.getValue()) {
                sb.append(String.join(",", s));
            }
            map.put(entry.getKey(), sb.toString());
        }
        return map;
    }
}