package in.om.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.om.component.TenantContext;
import in.om.component.Translator;
import in.om.response.ResponseBody;
import in.om.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Request filter will check each http request header token.
 */
@Component
@RequiredArgsConstructor
public class RequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, BadCredentialsException {
        final String requestTokenHeader = request.getHeader(JwtUtil.AUTHORIZATION);
        TokenDetails tokenDetails = null;
        String jwtToken = null;
        TenantContext.setCurrentOrganizationId("OM");
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token

        if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtUtil.BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                tokenDetails = jwtUtil.getTokenDetails(jwtToken);
                //Tenant
                //TenantContext.setCurrentTenantId(tokenDetails.getSystemClientId());
            } catch (IllegalArgumentException e) {
                handleException(response, Translator.toLocale("auth.invalid.username-password"));
                return;
            } catch (ExpiredJwtException e) {
                handleException(response, Translator.toLocale("auth.token.expired"));
                return;
            }
        }

        //Once we get the token validate it.
        if (tokenDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userService.loadUserByUsername(tokenDetails.getUserName());
            // if token is valid configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String message) throws IOException {
        ResponseBody responseBody = new ResponseBody(message,false, null);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
