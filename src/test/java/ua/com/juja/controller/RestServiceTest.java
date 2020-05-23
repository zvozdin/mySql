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
import ua.com.juja.config.SpringConfig;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.entity.Description;
import ua.com.juja.service.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}