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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    private final String CONTROLLER_PATH = "/game";

    public String setup(String username, String password) throws Exception {
        String token;
        JSONObject jsonBody;
        jsonBody = new JSONObject();
        jsonBody.put("username", username);
        jsonBody.put("password", password);

        MvcResult tokenResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
        return token = "Bearer " + JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");
    }

    @Test
    @Order(1)
    void testGetGames() throws Exception {
        String token = setup("Brice", "1234");
        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH).header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(2)
    void testGetGameById() throws Exception {
        String token = setup("Brice", "1234");
        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @Order(3)
    void AddGameUser() throws Exception {
        String token = setup("Brice", "1234");
        JSONObject jsonBody2 = new JSONObject();
        jsonBody2.put("name", "New Game");
        jsonBody2.put("publisher", "The New Publisher");
        jsonBody2.put("description", "This is the description.");
        jsonBody2.put("minPlayer", 2);
        jsonBody2.put("maxPlayer", 6);
        jsonBody2.put("averageDuration", 30);
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody2.toString())
                        .header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    void AddGameAdmin() throws Exception {
        String token = setup("Jean-Antoine", "1234");
        JSONObject jsonBody2 = new JSONObject();
        jsonBody2.put("name", "New Game");
        jsonBody2.put("publisher", "The New Publisher");
        jsonBody2.put("description", "This is the description.");
        jsonBody2.put("minPlayer", 2);
        jsonBody2.put("maxPlayer", 6);
        jsonBody2.put("averageDuration", 30);
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody2.toString())
                        .header("Authorization", token))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    void UpdateGameAdmin() throws Exception {
        String token = setup("Jean-Antoine", "1234");
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name", "New Game Modified");
        mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject3.toString())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("New Game Modified")));
    }

    @Test
    @Order(6)
    void DeleteGameAdmin() throws Exception {
        AddGameAdmin();
        String token = setup("Jean-Antoine", "1234");
        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/5")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }
}
