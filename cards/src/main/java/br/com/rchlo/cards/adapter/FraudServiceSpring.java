package br.com.rchlo.cards.adapter;

import br.com.rchlo.cards.application.service.fraud.FraudService;
import br.com.rchlo.cards.domain.fraud.FraudVerifierRepository;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class FraudServiceSpring extends FraudService {
	
	public FraudServiceSpring(final FraudVerifierRepository fraudVerifierRepository,
	                          final TransactionRepository transactionRepository) {
		super(fraudVerifierRepository, transactionRepository);
	}
	
}
