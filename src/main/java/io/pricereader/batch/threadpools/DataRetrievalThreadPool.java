package io.pricereader.batch.threadpools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.parser.Item;

@Component
public class DataRetrievalThreadPool {
	
	private ThreadPoolExecutor executor = null; 
	private ConcurrentLinkedQueue<Future<Item>> queue = new ConcurrentLinkedQueue<>();
	private AtomicBoolean isActive = new AtomicBoolean();
	
	@PostConstruct
	public void initialize() {
		destroy();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		queue.clear();
		isActive.set(false);
	}
	
	public void submitTask(DataRetrievalTask task) {
		isActive.set(true);
		queue.add(executor.submit(task));
	}
	
	public List<Item> retrieveData() throws InterruptedException, ExecutionException {
		List<Item> result = new ArrayList<>();
		if(queue != null && !queue.isEmpty()) {
			Iterator<Future<Item>> iterator = queue.iterator();
			while(iterator.hasNext()){
				Future<Item> item = iterator.next();
				result.add(item.get());
			}
		}
		//destroy();
		isActive.set(false);
		return result;
	}
	
	public boolean isJobRunning() {
		return isActive.get();
	}
	
	@PreDestroy
	public void destroy() {
		try {
			if(executor!=null && !executor.isTerminated()) {
				executor.shutdown();
				queue.clear();
				isActive.set(false);
				if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
					executor.shutdownNow();
					executor = null;
					queue.clear();
				}
			}
		} catch (InterruptedException e) {
			CommonConstants.LOGGER.error(e.getMessage());
			executor = null;
		}

	}
}
