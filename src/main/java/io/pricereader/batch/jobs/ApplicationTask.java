package io.pricereader.batch.jobs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;

import io.pricereader.batch.dataobject.JobDefinition;

public class ApplicationTask implements Runnable{
	
	private JobBuilderFactory jobBuilderFactory;
	private JobLauncher applicationJobLauncher;
	private PriceTrackerSteps steps;
	private JobDefinition jobDefinition;
	
	public ApplicationTask(JobDefinition jobDefinition, JobBuilderFactory jobBuilderFactory, JobLauncher jobLauncher, PriceTrackerSteps steps) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.applicationJobLauncher = jobLauncher;
		this.steps = steps;
		this.jobDefinition = jobDefinition;
	}
	
	@Override
	public void run() {
		Map<String, JobParameter> confMap = new HashMap<>();
	    confMap.put("time", new JobParameter(System.currentTimeMillis()));
	    confMap.put("jobName", new JobParameter(jobDefinition.getName()));
		
		JobParameters parameters = new JobParameters(confMap);
		
		Job job = this.jobBuilderFactory.get(this.jobDefinition.getName())
			.start(this.steps.configConstructionStep())
			.next(this.steps.dataConstructionStep())
			.build();

		try {
			applicationJobLauncher.run(job, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
