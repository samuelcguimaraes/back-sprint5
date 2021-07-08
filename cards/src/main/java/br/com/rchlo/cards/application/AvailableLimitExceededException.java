package br.com.rchlo.cards.application;

public class AvailableLimitExceededException extends RuntimeException {
	
	public AvailableLimitExceededException(final String message) {
		super(message);
	}
}