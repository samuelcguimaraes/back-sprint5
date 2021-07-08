package br.com.rchlo.cards.domain.fraud;

public class FraudException extends RuntimeException {
	
	public FraudException(final String message) {
		super(message);
	}
}
