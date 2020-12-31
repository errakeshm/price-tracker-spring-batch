package io.pricereader.batch.constants;

public class ApplicationConstants {
	private ApplicationConstants() {}
	public static final String JOB_CONFIG = "job.config";
	public enum JobName {
		flipkart("flipkart"),
		amazon("amazon");
		private String name;
		private JobName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
	}
}
