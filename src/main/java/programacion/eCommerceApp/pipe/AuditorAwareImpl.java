package programacion.eCommerceApp.pipe;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Optional<Authentication> authentication = Optional
            .ofNullable(SecurityContextHolder.getContext().getAuthentication());

        if (authentication.isEmpty())
            return Optional.of("Desconocido");

        return Optional.of(authentication.get().getName());
    }

}
