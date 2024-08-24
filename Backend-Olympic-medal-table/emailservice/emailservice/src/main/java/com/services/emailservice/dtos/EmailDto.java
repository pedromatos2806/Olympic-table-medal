package com.services.emailservice.dtos;



import com.services.emailservice.entities.Email;

import jakarta.validation.constraints.NotBlank;


public record EmailDto(@NotBlank String mailFrom,@NotBlank String mailTo,@NotBlank String mailSubject,@NotBlank String mailText) {

	public EmailDto(Email email) {
		this(email.getMailFrom(), email.getMailTo(), email.getMailSubject(),email.getMailText());
	}
}
