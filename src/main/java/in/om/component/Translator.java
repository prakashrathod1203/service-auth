package in.om.component;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Log4j2
public class Translator implements MessageSourceAware {

	private static MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String toLocale(String key) {
		try {
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(key, null, locale);
		} catch (NoSuchMessageException e) {
			log.error("Message not configured.", e);
		}
		return "";
   	}

	public static String toLocale(String key, Object... args) {
		try{
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(key, args, locale);
		} catch (NoSuchMessageException e) {
			log.error("Message not configured.", e);
		}
		return "";
	}
}
