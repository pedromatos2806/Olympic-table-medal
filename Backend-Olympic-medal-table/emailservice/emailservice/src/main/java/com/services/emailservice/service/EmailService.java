package com.services.emailservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.services.emailservice.dtos.EmailDto;
import com.services.emailservice.entities.Email;
import com.services.emailservice.enums.EmailStatus;
import com.services.emailservice.repositories.EmailRepository;

import jakarta.validation.Valid;

@Service
public class EmailService {
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;
	
	public List<EmailDto> listarEmails (){
		var emails = emailRepository.findAll();	
		List<EmailDto> emailsDto = new ArrayList<>();
		BeanUtils.copyProperties(emails, emailsDto);
		return emailsDto;
	}
	
	public Email sendEmail(@Valid EmailDto dto) {
		Email data = new Email(dto);
		data.setSendDateEmail(LocalDateTime.now());
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(dto.mailFrom());
		message.setTo(dto.mailTo());
		message.setSubject(dto.mailSubject());
		message.setText(dto.mailText());
		data.setStatus(EmailStatus.SENT);
		emailSender.send(message);
		emailRepository.save(data);
		System.out.println("******************************************************************************");
		System.out.println(data);
		System.out.println("******************************************************************************");
		return data;
	}
	
}
