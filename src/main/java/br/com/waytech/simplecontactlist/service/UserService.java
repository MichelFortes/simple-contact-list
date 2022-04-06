package br.com.waytech.simplecontactlist.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.waytech.simplecontactlist.dto.user.UserFormDto;
import br.com.waytech.simplecontactlist.model.Role;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.persistence.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User create(UserFormDto form) {
		User user = form.toModel();
		user.getRoles().add(Role.USER);
		return userRepository.save(user);
	}

	public User get(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
	}

	public Page<User> getAll(String name, Pageable pageable) {
		return userRepository.findByNameContaining(name, pageable);
	}

	public User update(Long id, UserFormDto dto) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
		dto.update(user);
		return user;
	}

	public void delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
		userRepository.delete(user);
	}

}
