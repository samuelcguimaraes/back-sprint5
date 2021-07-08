package br.com.rchlo.cards.adapter.repository;

import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.card.CardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardJpaRepository extends JpaRepository<Card, Long>, CardRepository {
	
	Optional<Card> findByHolderNameAndNumberAndExpirationAndSecurityCode(String holderName,
	                                                                     String number,
	                                                                     String expiration,
	                                                                     String securityCode);
}