package br.com.rchlo.cards.repository;

import br.com.rchlo.cards.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
	
	Optional<Card> findByHolderNameAndNumberAndExpirationAndSecurityCode(String holderName,
	                                                                     String number,
	                                                                     String expiration,
	                                                                     String securityCode);
}