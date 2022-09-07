package com.quest.etna;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    protected MockMvc mockMvc;

    @Sql(scripts = "/user-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/user-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testAuthenticate() throws Exception{
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("username", "toto");
        jsonBody.put("password", "1234");
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andExpect(status().isConflict());

        MvcResult tokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        String token = JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(MockMvcRequestBuilders.get("/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.role").exists());

    }

    @Test
    public void testUser() {
//      TODO after CRUD user
    }

    @Sql(scripts = "/user-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/user-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/address"))
                .andExpect(status().isUnauthorized());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("username", "brice");
        jsonBody.put("password", "1234");
        MvcResult tokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString()))
                .andReturn();
        String token = JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");
        mockMvc.perform(MockMvcRequestBuilders.get("/address")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        jsonBody.remove("username");
        jsonBody.remove("password");
        jsonBody.put("street", "rue de la gare");
        jsonBody.put("postalCode", "9220");
        jsonBody.put("city", "GAGNY");
        jsonBody.put("country", "FRANCE");
        mockMvc.perform(MockMvcRequestBuilders.post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody.toString())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/address/2")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());

        JSONObject jsonBody2 = new JSONObject();
        jsonBody2.put("username", "JeanAntoine");
        jsonBody2.put("password", "1234");
        MvcResult tokenResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody2.toString()))
                .andExpect(status().isOk())
                .andReturn();
        String token2 = JsonPath.read(tokenResult2.getResponse().getContentAsString(), "$.token");
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/1")
                        .header("Authorization", "Bearer " + token2))
                .andExpect(status().isOk());
    }
}
