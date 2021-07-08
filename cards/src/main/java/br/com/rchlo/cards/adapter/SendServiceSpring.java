package br.com.rchlo.cards.adapter;

import br.com.rchlo.cards.application.service.notification.SendService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SendServiceSpring implements SendService {
	
	private final MailSender mailSender;
	
	public SendServiceSpring(final MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMessage(final String to, final String subject, final String text) {
		final var message = new SimpleMailMessage();
		message.setFrom("noreply@rchlo.com.br");
		message.setTo(to);
		message.setSubject("Nova despesa: " + subject);
		message.setText(text);
		this.mailSender.send(message);   // para verificar o email enviado acesse: https://www.smtpbucket.com/emails.
		// Coloque noreply@rchlo.com.br em Sender e o email do cliente no Recipient.
	}
	
}
