package io.pricereader.batch.jobs;

import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.pricereader.batch.constants.ApplicationConstants;
import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.constants.ApplicationConstants.JobName;
import io.pricereader.batch.dataobject.Category;
import io.pricereader.batch.dataobject.JobConfiguration;
import io.pricereader.batch.parser.EcomVisitor;
import io.pricereader.batch.parser.EcomVisitorImpl;
import io.pricereader.batch.parser.Item;
import io.pricereader.batch.parser.UrlElement;
import io.pricereader.batch.threadpools.DataRetrievalTask;
import io.pricereader.batch.threadpools.DataRetrievalThreadPool;

@Component
public class PriceTrackerSteps {
	
	private static final String STATUS = "STATUS-";

	public static final String URL = "url";
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private DataRetrievalThreadPool dataRetrievalThreadPool;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	public Step configConstructionStep() {
		return this.stepBuilderFactory.get("configConstructionStep")
				.tasklet(configTasklet()).build();
	}
	
	public Step dataConstructionStep() {
		return this.stepBuilderFactory.get("dataConstructionStep")
				.tasklet(dataTasklet()).build();
	}
	
	private Tasklet configTasklet() {
		return (contribution, chunkContext) -> {
			String jobName = (String) chunkContext.getStepContext().getJobParameters().get("jobName");
			if(!this.dataRetrievalThreadPool.isJobRunning()) {
				this.dataRetrievalThreadPool.initialize();
				Category category = (Category) this.context.getBean(jobName);
				for(String url : category.getUrls()) {
					JobConfiguration jobConfiguration = new JobConfiguration();
					jobConfiguration.setUrl(url);
					jobConfiguration.setJobName(JobName.valueOf(jobName));
					UrlElement element = new UrlElement(jobConfiguration);
					this.dataRetrievalThreadPool.submitTask(new DataRetrievalTask(element));
				}
				chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(STATUS+jobName, false);
			} else {
				chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(STATUS+jobName, true);
				CommonConstants.LOGGER.info("Job is running. No other job is permitted");
			}
			
			return RepeatStatus.FINISHED;
		};
	}
	
	private Tasklet dataTasklet() {
		return (configuration, chunkContext) -> {
			String jobName = (String) chunkContext.getStepContext().getJobParameters().get("jobName");
			Boolean prevStepSkipped = (Boolean)chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get(STATUS+jobName);
			if(this.dataRetrievalThreadPool.isJobRunning() && (prevStepSkipped != null && !prevStepSkipped)) {
				List<Item> results = this.dataRetrievalThreadPool.retrieveData();
				for(Item item : results) {
					CommonConstants.LOGGER.info(item.toString());
				}
			}
			return RepeatStatus.FINISHED;
		};
	}
}
