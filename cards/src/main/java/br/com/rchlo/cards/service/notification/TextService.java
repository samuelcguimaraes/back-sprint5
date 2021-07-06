package br.com.rchlo.cards.service.notification;

import br.com.rchlo.cards.domain.Transaction;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextService {
	
	private final Configuration freemarker;
	
	public TextService(Configuration freemarker) {
		this.freemarker = freemarker;
	}
	
	public String generateTextNotification(Transaction transaction) {
		String notificationText;
		try {
			Template template = this.freemarker.getTemplate("expense-notification.ftl");
			Map<String, Object> data = new HashMap<>();
			data.put("transaction", transaction);
			StringWriter out = new StringWriter();
			template.process(data, out);
			notificationText = out.toString();
		} catch (IOException | TemplateException ex) {
			throw new IllegalStateException(ex);
		}
		
		return notificationText;
	}
}
