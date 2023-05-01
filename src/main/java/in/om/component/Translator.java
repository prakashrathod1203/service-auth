package in.om.component;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator implements MessageSourceAware {

	private static MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String toLocale(String key) {
	  	Locale locale = LocaleContextHolder.getLocale();
	 	return messageSource.getMessage(key, null, locale);
   	}

	public static String toLocale(String key, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, args, locale);
	}
}
