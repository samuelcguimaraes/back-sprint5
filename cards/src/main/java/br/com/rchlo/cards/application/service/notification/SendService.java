package br.com.rchlo.cards.application.service.notification;

public interface SendService {
	
	void sendMessage(final String to, final String subject, final String text);
	
}
