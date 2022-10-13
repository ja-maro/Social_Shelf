package com.quest.etna.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    private static String token;

    private JSONObject jsonBody;

    private final String CONTROLLER_PATH = "/message";

    @BeforeEach
    public  void setup() throws Exception {
        jsonBody = new JSONObject();
        jsonBody.put("username", "Brice");
        jsonBody.put("password", "1234");

        MvcResult tokenResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
        token = "Bearer " + JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");
    }

    @Test
    @Order(1)
    void getMessageByEventId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(2)
    void createMessage() throws Exception {
        jsonBody = new JSONObject();
        jsonBody.put("content", "the new message");
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH + "/1").contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody.toString()).header("Authorization", token)).andExpect(status().isCreated());
    }
}
