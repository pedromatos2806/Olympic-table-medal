package com.services.emailservice.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.services.emailservice.dtos.EmailDto;
import com.services.emailservice.dtos.MedalhaCommandSaida;
import com.services.emailservice.service.EmailService;

@Component
public class EmailListener {

	@Autowired
	EmailService emailService;

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	public void receberEmail(@Payload MedalhaCommandSaida emailCommand) {

		for (String email : emailCommand.emailsCadastradosNoPais()) {

			EmailDto emaildto = new EmailDto("puppy.rocha.ssa@gmail.com", email,
					"Olympics Table Medal - O país " + emailCommand.nomePais() + " recebeu uma nova medalha!",
					"O país  " + emailCommand.nomePais() + " ganhou mais uma medalha de "
							+ emailCommand.tipoMedalha() + " no esporte " + emailCommand.nomeEsporte() + ".");

			System.out.println(emaildto.toString());

			emailService.sendEmail(emaildto);
		}
	}

}
