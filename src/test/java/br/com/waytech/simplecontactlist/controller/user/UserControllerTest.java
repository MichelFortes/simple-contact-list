package br.com.waytech.simplecontactlist.controller.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.config.security.dto.AuthForm;
import br.com.waytech.simplecontactlist.config.security.dto.AuthResponseDto;
import br.com.waytech.simplecontactlist.controller.pub.PublicAuthController;

@ActiveProfiles(AppProfiles.TEST)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void shoulFailToGetUserOnFakeToke() throws Exception {
		
		String token = "werwerwerwfsdfsdfsdfsdfsdfsd";
		
		mockMvc.perform(get("https://localhost" + UserController.PATH)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, "bearer " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isForbidden());
	}
	
	@Test
	void shoulGetUser() throws Exception {
		
		mockMvc.perform(post("https://localhost" + PublicAuthController.PATH)
				.content(mapper.writeValueAsString(new AuthForm("user@gmail.com", "123456")))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.token").exists())
				.andExpect(jsonPath("$.tokenType").exists())
				.andDo(result -> {
					
					AuthResponseDto dto = mapper.readValue(result.getResponse().getContentAsByteArray(), AuthResponseDto.class);
					String token = dto.getToken();
					
					mockMvc.perform(get("https://localhost" + UserController.PATH)
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.header(HttpHeaders.AUTHORIZATION, "bearer " + token)
							.contentType(MediaType.APPLICATION_JSON_VALUE))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.id").exists())
							.andExpect(jsonPath("$.name").exists())
							.andExpect(jsonPath("$.email").exists());
				
				});
	}
	
	@Test
	void shoulUpdateUser() throws Exception {
		
		mockMvc.perform(post("https://localhost" + PublicAuthController.PATH)
				.content(mapper.writeValueAsString(new AuthForm("user@gmail.com", "123456")))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.token").exists())
				.andExpect(jsonPath("$.tokenType").exists())
				.andDo(result -> {
					
					AuthResponseDto dto = mapper.readValue(result.getResponse().getContentAsByteArray(), AuthResponseDto.class);
					String token = dto.getToken();
					
					mockMvc.perform(get("https://localhost" + UserController.PATH)
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.header(HttpHeaders.AUTHORIZATION, "bearer " + token)
							.contentType(MediaType.APPLICATION_JSON_VALUE))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.id").exists())
							.andExpect(jsonPath("$.name").exists())
							.andExpect(jsonPath("$.email").exists());
				
				});
	}

}
