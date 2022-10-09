package com.quest.etna.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    private final String CONTROLLER_PATH = "/event";

    public String setup(String username, String password) throws Exception {
        JSONObject jsonBody;
        jsonBody = new JSONObject();
        jsonBody.put("username", username);
        jsonBody.put("password", password);

        MvcResult tokenResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
        return "Bearer " + JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");
    }

    @Test
    @Order(1)
    void testGetAllEvents() throws Exception {
        String token = setup("Brice", "1234");
        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH).header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(2)
    void testGetByIdEvent() throws Exception {
        String token = setup("Brice", "1234");
        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @Order(3)
    void createEvent() throws Exception {
        String token = setup("Jean-Antoine", "1234");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("title", "new event");
        jsonBody.put("pitch", "Come all ! ");
        jsonBody.put("minPlayer", 2);
        jsonBody.put("maxPlayer", 4);
        jsonBody.put("startDate", "2022-10-21T18:00:00Z");
        jsonBody.put("cancellationDate", null);

        JSONObject jsonPlace = new JSONObject();
        jsonPlace.put("id", 2);
        jsonPlace.put("street", "22 rue de Rivoli");
        jsonPlace.put("postalCode", "75001");
        jsonPlace.put("city", "Paris");
        jsonPlace.put("country", "France");

        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("username", "Jean-Antoine");
        jsonPlayer.put("role", "ROLE_ADMIN");
        jsonPlayer.put("playerId", 2);
        jsonPlayer.put("email", "ja@mail.com");

        jsonPlace.put("player", jsonPlayer);
        jsonBody.put("place", jsonPlace);

        JSONObject jsonGame = new JSONObject();
        jsonGame.put("id", 2);
        jsonGame.put("name", "7 Wonders");
        jsonGame.put("publisher", "Repos Production");
        jsonGame.put("description", "Dans 7 Wonders Nouvelle Edition, prenez la tête de l'une des sept grandes cités du monde antique et laissez votre empreinte dans l'histoire des civilisations.");
        jsonGame.put("minPlayer", 3);
        jsonGame.put("maxPlayer", 7);
        jsonGame.put("averageDuration", 45);
        jsonBody.put("game", jsonGame);

        JSONObject jsonOrganizer = new JSONObject();
        jsonOrganizer.put("username", "Jean-Antoine");
        jsonOrganizer.put("role", "ROLE_ADMIN");
        jsonOrganizer.put("playerId", 2);
        jsonOrganizer.put("email", "ja@mail.com");
        jsonBody.put("organizer", jsonOrganizer);

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody.toString())
                .header("Authorization", token))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void updateEvent () throws Exception {
        createEvent();
        String token = setup("Jean-Antoine", "1234");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("title", "new event modified");
        mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("new event modified")));
    }

    @Test
    @Order(5)
    void deleteEvent () throws Exception {
        createEvent();
        String token = setup("Jean-Antoine", "1234");
        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/3")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("Message", is("Success")));
    }
}