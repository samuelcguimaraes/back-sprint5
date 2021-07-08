package br.com.rchlo.cards.application.service.notification;

import br.com.rchlo.cards.domain.transaction.Transaction;

public interface TextService {
	
	String generateTextNotification(final Transaction transaction);
	
}
