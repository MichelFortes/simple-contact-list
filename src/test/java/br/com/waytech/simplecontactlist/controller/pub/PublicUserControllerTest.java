package br.com.waytech.simplecontactlist.controller.pub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.dto.user.UserFormDto;

@ActiveProfiles(AppProfiles.TEST)
@SpringBootTest
@AutoConfigureMockMvc
class PublicUserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;

	final static String USER_NAME = "Joh"; //name min lenght = 3
	final static String USER_EMAIL = "galt.john@emai.com";
	final static String USER_PASS = "whoisjohngalt?";
	
	@Test
	void shouldCreateUser() throws JsonProcessingException, Exception {

		UserFormDto form = new UserFormDto();
		form.setName(USER_NAME);
		form.setEmail(USER_EMAIL);
		form.setPassword(USER_PASS);
		
		mockMvc.perform(post("https://localhost" + PublicUserController.PATH)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value(USER_NAME))
				.andExpect(jsonPath("$.email").value(USER_EMAIL));
	}
	
	@Test
	void shouldReturnBadRequestOnNullName()  throws JsonProcessingException, Exception {

		UserFormDto form = new UserFormDto();
		form.setName(null);
		form.setEmail(USER_EMAIL);
		form.setPassword(USER_PASS);

		mockMvc.perform(post("https://localhost" + PublicUserController.PATH)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	void shouldReturnBadRequestOnNullEmail()  throws JsonProcessingException, Exception {
		
		UserFormDto form = new UserFormDto();
		form.setName(USER_NAME);
		form.setEmail(null);
		form.setPassword(USER_PASS);
		
		mockMvc.perform(post("https://localhost" + PublicUserController.PATH)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Order(3)
	void shouldReturnBadRequestOnNullPass()  throws JsonProcessingException, Exception {
		
		UserFormDto form = new UserFormDto();
		form.setName(USER_NAME);
		form.setEmail(USER_PASS);
		form.setPassword(null);
		
		mockMvc.perform(post("https://localhost" + PublicUserController.PATH)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldReturnBadRequestOnNameShorterThenThree()  throws JsonProcessingException, Exception {
		
		UserFormDto form = new UserFormDto();
		form.setName("xx");
		form.setEmail(USER_EMAIL);
		form.setPassword(USER_PASS);
		
		mockMvc.perform(post("https://localhost" + PublicUserController.PATH)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isBadRequest());
	}

}
