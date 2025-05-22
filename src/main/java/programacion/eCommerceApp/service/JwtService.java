package programacion.eCommerceApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {

    private static final String SECRET_KEY = "AB1CD2EF3GH4IJ5KL6MN7OP8QR9ST10UV11WX12YZ13";

    public String getToken(UserDetails userDetails) {
        return getToken(new HashMap<>(), userDetails);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return Jwts
            .builder()
                .addClaims(extraClaims)
            .addClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String jwt) {
        return getClaim(jwt, Claims::getSubject);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String nombre = getUsernameFromToken(jwt);
        return (nombre.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private Claims getAllClaims(String jwt) {
        return Jwts
            .parser()
            .setSigningKey(getKey())
            .parseClaimsJws(jwt)
            .getBody();
    }

    public <T> T getClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String jwt) {
        return getClaim(jwt, Claims::getExpiration);
    }

    private boolean isTokenExpired(String jwt) {
        return getExpiration(jwt).before(new Date());
    }

}
