package io.pricereader.batch.parser;

import java.time.LocalDateTime;

public class Item {
	String name;
	JobName jobName;
	float price;
	LocalDateTime recordedTs;
	
	private Item(String name, JobName jobName, float price, LocalDateTime recordedTs) {
		this.name = name;
		this.jobName = jobName;
		this.price = price;
		this.recordedTs = recordedTs;
	}
	public interface JobName {
		Name setJobName(JobName jobName);
	}
	public interface Name {
		Price setName(String name);
	}
	public interface Price {
		RecordedTs setPrice(float price);
	}
	public interface RecordedTs {
		RecordedTs setRecordedTs(LocalDateTime recordedTs);
		Item build();
	}
	
	public static class ItemBuilder implements Name, JobName, Price, RecordedTs {
		String name;
		JobName jobName;
		float price;
		LocalDateTime recordedTs;
		
		public static Name getInstance() {
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
		public RecordedTs setRecordedTs(LocalDateTime recordedTs) {
			this.recordedTs = recordedTs;
			return this;
		}
		
		@Override
		public Item build() {
			return new Item(name, jobName, price, recordedTs);
		}
	}
	
	@Override
	public String toString() {
		return name +" "+price +" "+recordedTs; 
	}
}
