package br.com.rchlo.cards.adapter.repository;

import br.com.rchlo.cards.domain.fraud.FraudVerifier;
import br.com.rchlo.cards.domain.fraud.FraudVerifierRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudVerifierJpaRepository extends JpaRepository<FraudVerifier, Long>, FraudVerifierRepository {
	
	List<FraudVerifier> findByEnabledTrue();
}
