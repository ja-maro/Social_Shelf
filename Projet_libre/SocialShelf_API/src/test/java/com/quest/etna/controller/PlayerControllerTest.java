package com.quest.etna.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
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
//@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	private static String token;
	
	@Test
	@Order(1)
	public void testGetAll_WithUnauthPlayer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/player")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@Order(2)
	public void testGetAll_WithAuthUser() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("username", "Brice");
		jsonBody.put("password", "1234");
		MvcResult tokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON).content(jsonBody.toString())).andReturn();
		token = JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");

		//authenticated user gets 200
		mockMvc.perform(MockMvcRequestBuilders.get("/player").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	public void testDelete_WithWrongUser() throws Exception {
		// ROLE_USER gets 403 on delete other
		mockMvc.perform(MockMvcRequestBuilders.delete("/player/2").header("Authorization", "Bearer " + token))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@Order(4) 
	public void testDelete_WithAdmin() throws Exception {
		JSONObject jsonBody = new JSONObject();
		// authenticate as admin
		jsonBody.put("username", "Jean-Antoine");
		jsonBody.put("password", "1234");
		MvcResult tokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON).content(jsonBody.toString())).andReturn();
		token = JsonPath.read(tokenResult.getResponse().getContentAsString(), "$.token");

		// ROLE_ADMIN gets 200 on delete other
		mockMvc.perform(MockMvcRequestBuilders.delete("/player/1").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}

}
