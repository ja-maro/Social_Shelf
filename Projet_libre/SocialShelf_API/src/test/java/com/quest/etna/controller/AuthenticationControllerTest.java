package com.quest.etna.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	@Autowired
	protected MockMvc mockMvc;

	private static JSONObject jsonBody;

	@BeforeAll
	public static void setup() throws Exception {
		jsonBody = new JSONObject();
		jsonBody.put("username", "Brice");
		jsonBody.put("password", "1234");
		jsonBody.put("email", "brice@brice.fr");
	}

	@Test
	public void testRegisterNewUser() throws Exception {
		JSONObject jsonBody2 = new JSONObject();
		jsonBody2.put("username", "toto");
		jsonBody2.put("password", "1234");
		jsonBody2.put("email", "toto@mail.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonBody2.toString())).andExpect(status().isCreated());
	}

	@Test
	public void testRegisterDuplicateUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonBody.toString())).andExpect(status().isConflict());
	}

	@Test
	public void testAuthenticate() throws Exception {
		MvcResult tokenResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody.toString()))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
	}

	@Test
	public void testMe() throws Exception {
		MvcResult tokenResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody.toString()))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		String token = JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");

		mockMvc.perform(MockMvcRequestBuilders.get("/me").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.email").exists()).andExpect(jsonPath("$.role").exists());
	}
}
