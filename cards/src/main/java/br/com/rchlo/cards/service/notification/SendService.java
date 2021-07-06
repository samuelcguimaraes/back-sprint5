package br.com.rchlo.cards.service.notification;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SendService {
	
	private final MailSender mailSender;
	
	public SendService(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMessage(String to, String subject, String text) {
		var message = new SimpleMailMessage();
		message.setFrom("noreply@rchlo.com.br");
		message.setTo(to);
		message.setSubject("Nova despesa: " + subject);
		message.setText(text);
		this.mailSender.send(message);   // para verificar o email enviado acesse: https://www.smtpbucket.com/emails.
		// Coloque noreply@rchlo.com.br em Sender e o email do cliente no Recipient.
	}
	
}
