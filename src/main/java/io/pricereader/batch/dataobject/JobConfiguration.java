package io.pricereader.batch.dataobject;

import io.pricereader.batch.constants.ApplicationConstants.JobName;

public class JobConfiguration {
	private String url;
	private String name;
	private JobName jobName;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public JobName getJobName() {
		return jobName;
	}
	public void setJobName(JobName jobName) {
		this.jobName = jobName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "JobConfiguration [url=" + url + ", jobName=" + jobName + ", name=" +name+"]";
	} 
}
