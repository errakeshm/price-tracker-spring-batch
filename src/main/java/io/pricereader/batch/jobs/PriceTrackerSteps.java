package io.pricereader.batch.jobs;

import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.dao.ProductDAO;
import io.pricereader.batch.constants.ApplicationConstants.JobName;
import io.pricereader.batch.dataobject.Category;
import io.pricereader.batch.dataobject.JobConfiguration;
import io.pricereader.batch.dataobject.PriceDO;
import io.pricereader.batch.dataobject.ProductDO;
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
	
	@Autowired
	private ProductDAO productDAO;
	
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
				category.getUrls().entrySet().stream().forEach(entry->{
					JobConfiguration jobConfiguration = new JobConfiguration();
					jobConfiguration.setName(entry.getKey());
					jobConfiguration.setUrl(entry.getValue());
					jobConfiguration.setJobName(JobName.valueOf(jobName));
					UrlElement element = new UrlElement(jobConfiguration);
					this.dataRetrievalThreadPool.submitTask(new DataRetrievalTask(element));
				});
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
					ProductDO productRetrieved = null;
					if((productRetrieved=this.productDAO.getProductByName(item.getName())) == null) {
						ProductDO product = new ProductDO();
						product.setName(item.getName());
						product.setCategory(item.getJobName().toString());
						product.setUrl(item.getUrl());
						product.setProductId(this.productDAO.addProduct(product));
						productRetrieved = product;
					}
					CommonConstants.LOGGER.info("Product Retrieved : " + productRetrieved.toString());
					CommonConstants.LOGGER.info("Item retrieved : "+item.toString());
					this.productDAO.addPrice(new PriceDO(productRetrieved.getProductId(), item.getPrice(), item.getRecordedTs()));
				}
			}
			return RepeatStatus.FINISHED;
		};
	}
}
