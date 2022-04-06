package br.com.waytech.simplecontactlist.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.model.Role;
import br.com.waytech.simplecontactlist.model.User;

@DataJpaTest
@ActiveProfiles(AppProfiles.TEST)
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void shoulGetUserById() {
		
		User user = new User();
		user.setName("John Galt");
		user.setEmail("user@domain.com");
		user.setPassword("123456");
		user.getRoles().add(Role.USER);
		
		entityManager.persist(user);
		
		User entity = userRepository.findById(1L).orElse(null);
		assertNotNull(entity);
	}


}
