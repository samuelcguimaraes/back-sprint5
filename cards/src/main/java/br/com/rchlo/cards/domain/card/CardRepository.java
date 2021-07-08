package br.com.rchlo.cards.domain.card;

import java.util.Optional;

public interface CardRepository {
	
	Optional<Card> findByHolderNameAndNumberAndExpirationAndSecurityCode(String holderName,
	                                                                     String number,
	                                                                     String expiration,
	                                                                     String securityCode);
}