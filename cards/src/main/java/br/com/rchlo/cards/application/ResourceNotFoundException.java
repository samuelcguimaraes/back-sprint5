package br.com.rchlo.cards.application;

public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(final String message) {
		super(message);
	}
}