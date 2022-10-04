package com.quest.etna.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;

import com.jayway.jsonpath.JsonPath;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShelfControllerTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	private static String token;

	private JSONObject jsonBody;
	
	private final String CONTROLLER_PATH = "/shelf";

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
	public void testGetShelf() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH).header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[*].id", containsInAnyOrder(1)))
		.andExpect(jsonPath("$[*].name", containsInAnyOrder("Uno")))
		.andExpect(jsonPath("$[*].publisher", containsInAnyOrder("Mattel")));
	}
	
	@Test
	@Order(2)
	public void testAddToShelf() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/2").header("Authorization",token))
		.andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH).header("Authorization", token))
		.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
		.andExpect(jsonPath("$[*].name", containsInAnyOrder("Uno", "7 Wonders")))
		.andExpect(jsonPath("$[*].publisher", containsInAnyOrder("Mattel", "Repos Production")));
	}
	
	@Test
	@Order(3)
	public void testAddToShelf_WhenAlreadyHas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/2").header("Authorization", token))
		.andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/2").header("Authorization", token))
		.andExpect(status().isConflict());
	}
	
	@Test
	@Order(4)
	public void testRemoveFromShelf() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/1").header("Authorization", token))
		.andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH).header("Authorization", token))
		.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	@Order(5)
	public void testRemoveFromShelf_WhenHasNot() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/1").header("Authorization", token))
		.andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/1").header("Authorization", token))
		.andExpect(status().isNotFound());
	}

}
