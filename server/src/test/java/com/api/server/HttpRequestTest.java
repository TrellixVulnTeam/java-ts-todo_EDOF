package com.api.server;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestTest {

	private final String testTodo = "This is only a test todo";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/api/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}

	@Test 
	public void endpointDoesNotExist() throws Exception {
		this.mockMvc.perform(get("/todos")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void createTodo() throws Exception {
		this.mockMvc.perform( MockMvcRequestBuilders
      .post("/api/todos")
      .content(asJsonString(new Todo("444", testTodo, false))).contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON));
	}

	@Test
	public void getAllTodos() throws Exception {
		this.mockMvc.perform(get("/api/todos")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("id"))).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test 
	public void checkNewTodo() throws Exception {
			createTodo();

			this.mockMvc.perform(get("/api/todos")).andExpect(content().string(containsString(testTodo)));
      
	}

	@Test
	public void getOneTodo() throws Exception {
		createTodo();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/{id}", "444").content(contains(testTodo)).contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void cantFindTodo() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/{id}", "447848727427842").contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void changeTodoComplete() throws Exception {
		createTodo();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/{id}", "444").content(asJsonString(new Todo("444", testTodo, true))).contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void deleteAllTodos() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos")).andExpect(status().isNoContent());

		this.mockMvc.perform(get("/api/todos")).andExpect(status().isNoContent());
	}

	@Test
	public void deleteTodo() throws Exception {
			createTodo();

			this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/444")).andExpect(status().isNoContent());

			this.mockMvc.perform(get("/api/todos")).andExpect(content().string(CoreMatchers.not(containsString(testTodo))));
	}


	public static String asJsonString(final Object obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
