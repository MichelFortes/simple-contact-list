package br.com.waytech.simplecontactlist.controller.pub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.config.security.dto.AuthForm;

@ActiveProfiles(AppProfiles.TEST)
@SpringBootTest
@AutoConfigureMockMvc
class PublicAuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void shouldAuthenticate() throws JsonProcessingException, Exception {
		
		String email = "user@gmail.com";
		String pass = "123456";
		
		AuthForm authForm = new AuthForm(email, pass);
		
		mockMvc.perform(post("https://localhost" + PublicAuthController.PATH)
				.content(mapper.writeValueAsString(authForm))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.email").value(email))
				.andExpect(jsonPath("$.tokenType").value("bearer"))
                .andExpect(jsonPath("$.token").exists());
	}

	@Test
	void shouldFailToAuthenticateOnWrongEmail() throws JsonProcessingException, Exception {
		
		AuthForm authForm = new AuthForm("xxxxxx@gmail.com", "123456");
		
		mockMvc.perform(post("https://localhost" + PublicAuthController.PATH)
				.content(mapper.writeValueAsString(authForm))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	void shouldFailToAuthenticateOnWrongPass() throws JsonProcessingException, Exception {
		
		AuthForm authForm = new AuthForm("user@gmail.com", "xxxxxxxx");
		
		mockMvc.perform(post("https://localhost" + PublicAuthController.PATH)
				.content(mapper.writeValueAsString(authForm))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

}
