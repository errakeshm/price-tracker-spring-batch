package io.pricereader.batch.validators;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class JobConfigValidator {
	
	private static final Predicate<String> flipkartUrlValidator = (url) -> {
		Pattern pattern = Pattern.compile("(http://|https://){0,1}flipkart.com");
		return pattern.matcher(url).find();
	};
	
	@Bean
	public JobParametersValidator jobconfigvalidator() {
		return new JobParametersValidator() {
			@Override
			public void validate(JobParameters parameters) throws JobParametersInvalidException {
				String url = parameters.getString("url");
				if(url !=null && !flipkartUrlValidator.test(url)) {
					throw new JobParametersInvalidException("URL not supported");
				}
			}
		};
	}
}
