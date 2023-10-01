package in.om.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.om.entities.record.UserResource;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private static final String RESOURCE = "resource";

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.expiration.in.minutes}")
    private int jwtExpirationInMinutes;

    public TokenDetails getTokenDetails(String token) {
        log.debug("getTokenDetails() in JwtUtil, Request : {}", token);
        if (token != null && token.startsWith(JwtUtil.BEARER)) {
            token = token.substring(7);
        }
        String loginId = getClaimFromToken(token, Claims::getSubject);
        UserResource userResource = getUserResourceFromToken(token);

        // Id, UserResource
        TokenDetails tokenDetails = new TokenDetails(loginId, userResource);
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
    public UserResource getUserResourceFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        LinkedHashMap linkedHashMap = claims.get(RESOURCE, LinkedHashMap.class);
        return objectMapper.convertValue(linkedHashMap, UserResource.class);
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
        claims.put(RESOURCE, userPrincipal.getResource());
        String token  = doGenerateToken(claims, userPrincipal.getLoginId());
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

    public Boolean validateToken(String token, TokenDetails tokenDetails, UserDetails userDetails) {
        return (tokenDetails.getLoginId().equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
