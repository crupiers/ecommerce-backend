package programacion.eCommerceApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import programacion.eCommerceApp.pipe.AuditorAwareImpl;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class eCommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(eCommerceApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

}
