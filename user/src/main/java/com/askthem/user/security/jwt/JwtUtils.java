package com.askthem.user.security.jwt;
import com.askthem.user.exception.CustomException;
import com.askthem.user.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

//    @Value("${jwt.expiry}")
    private int jwtExpiry = 604800000; // 7 days

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(" generateJwtToken --- "+userPrincipal);
        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiry))
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws CustomException, Exception{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
//            throw new CustomException("MalformedJwtException", 401);
            System.out.println("Invalid JWT token: {}"+ e.getMessage());
//            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (SignatureException e) {
//            throw new CustomException("SignatureException", 401);
            System.out.println("Invalid JWT signature: {}"+ e.getMessage());
//            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
//            throw new CustomException("ExpiredJwtException", 401);
            System.out.println("JWT token is expired: {}"+ e.getMessage());
//            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
//            throw new CustomException("UnsupportedJwtException", 401);
            System.out.println("JWT token is unsupported: {}"+ e.getMessage());
//            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
//            throw new CustomException("IllegalArgumentException", 401);
            System.out.println("JWT claims string is empty: {}"+ e.getMessage());
//            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (ClassCastException e) {
//            throw new CustomException("ClassCastException", 401);
            System.out.println("JWT claims string is empty: {}"+ e.getMessage());
//            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
