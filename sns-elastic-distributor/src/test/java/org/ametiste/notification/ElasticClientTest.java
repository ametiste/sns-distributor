package org.ametiste.notification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {Boot.class, Dummy.class})
@IntegrationTest("server.port:8088")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class ElasticClientTest {

    public static final MediaType MEDIA_TYPE_UTF8 = new MediaType("application", "json", Charset.forName("UTF-8"));

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws IOException, URISyntaxException {

        this.mockMvc = webAppContextSetup(this.wac).build();

    }


    @Test
    @DirtiesContext
    public void testDumpWithAllExceptions() throws Exception {

        String body1 = "{\"id\":\"11111111-1111-1111-1111-111111111111\",    \"date\": 1436165393214,\n" +
                "    \"type\": \"RANDOM_TEST_REPORT\",\n" +
                "    \"sender\": \"RANDOM_TEST_REPORT_SENDER\", \"content\": {}}" ;

        String body2 = "{\"id\":\"11111111-1111-1111-1111-111111111112\",    \"date\": 1436165393214,\n" +
                "    \"type\": \"RANDOM_TEST_REPORT\",\n" +
                "    \"sender\": \"RANDOM_TEST_REPORT_SENDER\", \"content\": {}}" ;

        this.mockMvc
                .perform(
                        post("/").content(body1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        this.mockMvc
                .perform(
                        post("/").content(body2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


        this.mockMvc
                .perform(
                        get("/fails").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.forClass").exists())
                .andExpect(jsonPath("$.forClass").value("*"))
                .andExpect(jsonPath("$.failedReports").exists())
                .andExpect(jsonPath("$.failedReports").isArray())
                .andExpect(jsonPath("$.failedReports[0]").exists())
                .andExpect(jsonPath("$.failedReports[1]").exists())
                .andExpect(jsonPath("$.failedReports[2]").doesNotExist())
        ;

    }

    @Test
    @DirtiesContext
    public void testDumpWithExceptionName() throws Exception {

        String body1 = "{\"id\":\"11111111-1111-1111-1111-111111111111\",    \"date\": 1436165393214,\n" +
                "    \"type\": \"RANDOM_TEST_REPORT\",\n" +
                "    \"sender\": \"RANDOM_TEST_REPORT_SENDER\", \"content\": {}}" ;

        String body2 = "{\"id\":\"11111111-1111-1111-1111-111111111112\",    \"date\": 1436165393214,\n" +
                "    \"type\": \"RANDOM_TEST_REPORT\",\n" +
                "    \"sender\": \"RANDOM_TEST_REPORT_SENDER\", \"content\": {}}" ;

        this.mockMvc
                .perform(
                        post("/").content(body1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        this.mockMvc
                .perform(
                        post("/").content(body2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


        this.mockMvc
                .perform(
                        get("/fails/ExceptionOne").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.forClass").exists())
                .andExpect(jsonPath("$.forClass").value("ExceptionOne"))
                .andExpect(jsonPath("$.failedReports").exists())
                .andExpect(jsonPath("$.failedReports").isArray())
                .andExpect(jsonPath("$.failedReports[0]").exists())
                .andExpect(jsonPath("$.failedReports[1]").doesNotExist())
        ;

    }


}
