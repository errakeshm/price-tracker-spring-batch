package io.pricereader.batch.parser;

import java.time.LocalDateTime;
import io.pricereader.batch.constants.ApplicationConstants.JobName;

public class Item {
	String name;
	JobName jobName;
	float price;
	LocalDateTime recordedTs;
	String url;
	
	private Item(String name, JobName jobName, float price, LocalDateTime recordedTs, String url) {
		this.name = name;
		this.jobName = jobName;
		this.price = price;
		this.recordedTs = recordedTs;
		this.url = url;
	}
	public interface IJobName {
		Name setJobName(JobName jobName);
	}
	public interface Name {
		Price setName(String name);
	}
	public interface Price {
		RecordedTs setPrice(float price);
	}
	public interface RecordedTs {
		Url setRecordedTs(LocalDateTime recordedTs);
	}
	public interface Url {
		Url setUrl(String url);
		Item build();
	}
	
	public static class ItemBuilder implements Name, IJobName, Price, RecordedTs, Url {
		String name;
		JobName jobName;
		float price;
		LocalDateTime recordedTs;
		String url;
		
		public static IJobName getInstance() {
			return new ItemBuilder();
		}
		@Override
		public Name setJobName(JobName jobName) {
			this.jobName = jobName;
			return this;
		}
		
		@Override
		public Price setName(String name){
			this.name = name;
			return this;
		}
		
		@Override
		public RecordedTs setPrice(float price) {
			this.price = price;
			return this;
		}
	
		@Override
		public Url setRecordedTs(LocalDateTime recordedTs) {
			this.recordedTs = recordedTs;
			return this;
		}
		
		@Override
		public Url setUrl(String url) {
			this.url = url;
			return this;
		}
		
		@Override
		public Item build() {
			return new Item(name, jobName, price, recordedTs, url);
		}
	}
	public JobName getJobName() {
		return jobName;
	}
	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}
	public LocalDateTime getRecordedTs() {
		return recordedTs;
	}
	public String getUrl() {
		return url;
	}
	@Override
	public String toString() {
		return name +" "+price +" "+recordedTs; 
	}
}
