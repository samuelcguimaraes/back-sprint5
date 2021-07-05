package br.com.rchlo.cards.repository;

import br.com.rchlo.cards.domain.FraudVerifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudVerifierRepository extends JpaRepository<FraudVerifier, Long> {
	
	List<FraudVerifier> findByEnabledTrue();
}
