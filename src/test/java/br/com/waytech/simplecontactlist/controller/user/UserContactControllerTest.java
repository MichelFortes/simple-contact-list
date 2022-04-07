package br.com.waytech.simplecontactlist.controller.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.config.security.dto.AuthForm;
import br.com.waytech.simplecontactlist.config.security.dto.AuthResponseDto;
import br.com.waytech.simplecontactlist.controller.pub.PublicAuthController;
import br.com.waytech.simplecontactlist.dto.contact.ContactFormDto;

@ActiveProfiles(AppProfiles.TEST)
@SpringBootTest
@AutoConfigureMockMvc
class UserContactControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	final static String CONTACT_NAME = "Own"; //name min lenght = 3
	final static String CONTACT_EMAIL = "own.carl@emai.com";
	final static String CONTACT_ADDRESS = "own.carl@emai.com";
	
	@Test
	void shouldCreateContact() throws JsonProcessingException, Exception {
		
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
					
					ClassLoader classLoader = getClass().getClassLoader();
					File file = new File(classLoader.getResource("contact.png").getFile());		
					
					ContactFormDto form = new ContactFormDto();
					form.setName(CONTACT_NAME);
					form.setEmail(CONTACT_EMAIL);
					form.setAddress(CONTACT_ADDRESS);
					for(int i=1000; i <= 10000; i = i + 1000) form.getPhones().add(String.valueOf(i));
					
					MockMultipartFile mockFile = new MockMultipartFile("file", "file", MediaType.IMAGE_PNG.toString(), new FileInputStream(file));
					MockMultipartFile mockForm = new MockMultipartFile("form", "form", MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsBytes(form)); 

					mockMvc.perform(multipart("https://localhost" + UserContactController.PATH)
							.file(mockFile)
							.file(mockForm)				
							.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
							.header(HttpHeaders.AUTHORIZATION, "bearer " + token))
							.andExpect(status().isCreated())
							.andExpect(jsonPath("$.id").exists())
							.andExpect(jsonPath("$.name").value(CONTACT_NAME))
							.andExpect(jsonPath("$.email").value(CONTACT_EMAIL))
							.andExpect(jsonPath("$.address").value(CONTACT_ADDRESS))
							.andExpect(jsonPath("$.phones").isNotEmpty())
							.andExpect(jsonPath("$.pictureUrl").exists());
				});
		
	}

}
