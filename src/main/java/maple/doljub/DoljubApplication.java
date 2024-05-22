package maple.doljub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "memberAuditorAware")
@SpringBootApplication
public class DoljubApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoljubApplication.class, args);
	}

}
