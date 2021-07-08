package br.com.rchlo.cards.adapter;

import br.com.rchlo.cards.application.service.notification.TextService;
import br.com.rchlo.cards.domain.transaction.Transaction;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextServiceFreemarker implements TextService {
	
	private final Configuration freemarker;
	
	public TextServiceFreemarker(final Configuration freemarker) {
		this.freemarker = freemarker;
	}
	
	public String generateTextNotification(final Transaction transaction) {
		final String notificationText;
		try {
			final Template template = this.freemarker.getTemplate("expense-notification.ftl");
			final Map<String, Object> data = new HashMap<>();
			data.put("transaction", transaction);
			final StringWriter out = new StringWriter();
			template.process(data, out);
			notificationText = out.toString();
		} catch (final IOException | TemplateException ex) {
			throw new IllegalStateException(ex);
		}
		
		return notificationText;
	}
}
