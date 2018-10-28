package io.jlloyd.utilities.artifact.storage.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

/**
 * Main application class used to boostrap and configure the application.
 * 
 * @author b14951
 */
@SpringBootApplication
public class Application {

	public static final String ARTIFACT_PATH = "artifactPath";
	public static final String ATTACHMENT_FILENAME = "attachment; filename=";
	public static final String FILENAME = "filename";
	public static final String NO_CACHE = "no-cache";
	public static final String NO_CACHE_NO_STORE_MUST_REVALIDATE = "no-cache, no-store, must-revalidate";

	/**
	 * Bootstraps the application.
	 * 
	 * @param args
	 *            - Array of args.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Creates a new {@link RestTemplate} with a {@link ByteArrayHttpMessageConverter} for handling requests.
	 * 
	 * @return A new {@link RestTemplate}.
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new HttpMessageConverters(new ByteArrayHttpMessageConverter()).getConverters());
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth.inMemoryAuthentication()
				.withUser("admin").password("admin").roles("ADMIN", "USER", "ACTUATOR").and()
				.withUser("user").password("user").roles("USER");
			// @formatter:on
		}
	}
}