package br.com.rchlo.cards.domain.fraud;

import java.util.List;

public interface FraudVerifierRepository {
	
	List<FraudVerifier> findByEnabledTrue();
}
