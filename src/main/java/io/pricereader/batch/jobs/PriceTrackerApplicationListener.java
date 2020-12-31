package io.pricereader.batch.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="scheduler.properties")
public class PriceTrackerApplicationListener implements ApplicationListener<ApplicationReadyEvent>{
	
	@Autowired
	private OnlineSchedulerDelegator onlineSchedulerDelegator;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.onlineSchedulerDelegator.delegateTask();
	}
}
