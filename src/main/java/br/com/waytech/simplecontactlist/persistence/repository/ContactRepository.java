package br.com.waytech.simplecontactlist.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.waytech.simplecontactlist.model.Contact;
import br.com.waytech.simplecontactlist.model.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByIdAndUser(Long id, User user);

	Page<Contact> findByNameContainingAndUser(String name, User user, Pageable pageable);
	
}
