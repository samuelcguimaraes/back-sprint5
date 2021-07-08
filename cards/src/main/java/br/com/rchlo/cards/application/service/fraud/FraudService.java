package br.com.rchlo.cards.application.service.fraud;

import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.fraud.FraudVerifier;
import br.com.rchlo.cards.domain.fraud.FraudVerifierRepository;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FraudService {
	
	private final FraudVerifierRepository fraudVerifierRepository;
	private final TransactionRepository transactionRepository;
	
	public FraudService(final FraudVerifierRepository fraudVerifierRepository,
	                    final TransactionRepository transactionRepository) {
		this.fraudVerifierRepository = fraudVerifierRepository;
		this.transactionRepository = transactionRepository;
	}
	
	public void checkFraud(final TransactionRequestDto transactionRequest, final Card card) {
		
		final List<FraudVerifier> enabledFraudVerifiers = this.fraudVerifierRepository.findByEnabledTrue();
		
		final var verifications = enabledFraudVerifiers
				                          .stream()
				                          .map(fraudVerifier -> fraudVerifier.getType().getVerifier())
				                          .collect(Collectors.toList());
		
		verifications.forEach(verifiable -> verifiable.check(transactionRequest, card, this.transactionRepository));
	}
}
