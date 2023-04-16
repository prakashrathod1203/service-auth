package in.om.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT utilities.
 */
@Component
@Log4j2
public class JwtUtil implements Serializable {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final long serialVersionUID = -2550185165626007488L;
    private final String secret = "secret";

    @Value("${jwt.expiration.in.minutes}")
    private int jwtExpirationInMinutes;

    public TokenDetails getTokenDetails(String token) {
        log.debug("getTokenDetails() in JwtUtil, Request : {}", token);
        if (token != null && token.startsWith(JwtUtil.BEARER)) {
            token = token.substring(7);
        }
        String subject = getClaimFromToken(token, Claims::getSubject);
        String [] tokenArray = subject.split("-");
        // Id, ClientSystemId, Username
        TokenDetails tokenDetails = new TokenDetails(Integer.valueOf(tokenArray[0].trim()), Long.valueOf(tokenArray[1].trim()), tokenArray[2].trim());
        log.debug("getTokenDetails() in JwtUtil, Response : {}", tokenDetails);
        return tokenDetails;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserPrincipal userPrincipal) {
        log.debug("generateToken() in JwtUtil, Request : {}", userPrincipal);
        Map<String, Object> claims = new HashMap();
        String subject = String.format("%d-%d-%s",userPrincipal.getId(), userPrincipal.getClientSystemId(), userPrincipal.getUsername());
        String token  = doGenerateToken(claims, subject);
        log.debug("generateToken() in JwtUtil, Response : {}", token);
        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(jwtExpirationInMinutes).toMillis())).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final TokenDetails tokenDetails = getTokenDetails(token);
        return (tokenDetails.getUserName().equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
