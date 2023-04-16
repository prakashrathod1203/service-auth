package in.om.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import in.om.utility.ApplicationConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocaleResolverConfig extends AcceptHeaderLocaleResolver {
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		if (request.getHeader("Accept-Language") == null) {
			return Locale.getDefault();
		}
		List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
		Locale locale = Locale.lookup(list, ApplicationConstants.LOCALES);
		return locale;
	}
}
