package ua.com.juja.controller;

import org.hamcrest.Matchers;
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
import ua.com.juja.service.Service;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestServiceTest {

    private MockMvc mockMvc;

    @Mock
    private Service service;

    @InjectMocks
    private RestService restService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockMvc = standaloneSetup(restService).build();
    }

    @Test
    public void test1_Menu() throws Exception {
        // when
        when(service.getCommands()).thenReturn(Arrays.asList("test"));

        // then
        mockMvc.perform(get("/menu/content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(Arrays.asList("test"))));
    }
}