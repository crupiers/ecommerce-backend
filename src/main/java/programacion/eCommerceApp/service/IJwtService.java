package programacion.eCommerceApp.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String getToken(UserDetails userDetails);

    String getUsernameFromToken(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);

}
