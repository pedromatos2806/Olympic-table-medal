package com.services.emailservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.emailservice.entities.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{

}
