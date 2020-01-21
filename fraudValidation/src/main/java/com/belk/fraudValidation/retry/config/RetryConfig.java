package com.belk.fraudValidation.retry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
	
	@Value("${app.retry.maxattempts}")
	private int maxAttempts;
	@Value("${app.retry.backOffDelay}")
	private long backOffDelay;
	
	 @Bean
	  public RetryTemplate retryTemplate() {
		
		 SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
	    retryPolicy.setMaxAttempts(maxAttempts);
	 
	    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
	    backOffPolicy.setBackOffPeriod(backOffDelay); 
	 
	    RetryTemplate template = new RetryTemplate();
	    template.setRetryPolicy(retryPolicy);
	    template.setBackOffPolicy(backOffPolicy);
	 
	    return template;
	  }

}
