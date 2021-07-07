package br.com.rchlo.cards.service.fraud;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.FraudVerifier;
import br.com.rchlo.cards.dto.TransactionRequestDto;
import br.com.rchlo.cards.exception.FraudException;
import br.com.rchlo.cards.repository.FraudVerifierRepository;
import br.com.rchlo.cards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FraudService {
	
	private final FraudVerifierRepository fraudVerifierRepository;
	private final TransactionRepository transactionRepository;
	
	public FraudService(final FraudVerifierRepository fraudVerifierRepository,
	                    final TransactionRepository transactionRepository) {
		this.fraudVerifierRepository = fraudVerifierRepository;
		this.transactionRepository = transactionRepository;
	}
	
	public void checkFraud(final TransactionRequestDto transactionRequest, final Card card) throws FraudException {
		final List<FraudVerifier> enabledFraudVerifiers = this.fraudVerifierRepository.findByEnabledTrue();
		
		final var verifications = enabledFraudVerifiers
				                          .stream()
				                          .map(fv -> fv.getType().getVerifier()).collect(Collectors.toList());
		
		//verifications.forEach(verifiable -> verifiable.check(transactionRequest, card, this.transactionRepository));
		for (final Verifiable verifiable : verifications) {
			verifiable.check(transactionRequest, card, this.transactionRepository);
		}
	}
}
