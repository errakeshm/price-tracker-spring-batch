package io.pricereader.batch.jobs;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pricereader.batch.dataobject.JobDefinition;

@Component
public class OnlineScheduler {
	
	private Map<String, ApplicationTask> jobMap = new ConcurrentHashMap<>();
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private PriceTrackerSteps priceTrackerSteps;
	
	public ApplicationTask createApplicationTask(JobDefinition jobDefinition) {
		if (this.jobMap.get(jobDefinition.getName()) == null)
			this.jobMap.put(jobDefinition.getName(), 
					new ApplicationTask(jobDefinition, jobBuilderFactory, jobLauncher, priceTrackerSteps));
		return this.jobMap.get(jobDefinition.getName());
	}
}
