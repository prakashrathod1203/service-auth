package in.om.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
	@Autowired
	private static ResourceBundleMessageSource messageSource;


	   public static String toLocale(String key) {
	      Locale locale = LocaleContextHolder.getLocale();
	      return messageSource.getMessage(key, null, locale);
	   }

	public static String toLocale(String key, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, args, locale);
	}
}
