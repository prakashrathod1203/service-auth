package tech.sarthee.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleResolver extends AcceptHeaderLocaleResolver {

    public static final List<Locale> LOCALES = List.of(new Locale("en"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String acceptLanguageHeader = request.getHeader("Accept-Language");
        if (acceptLanguageHeader == null || acceptLanguageHeader.isEmpty()) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
        return Locale.lookup(languageRanges, LOCALES);
    }
}
