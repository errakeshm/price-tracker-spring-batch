package io.pricereader.batch.parser;

import io.pricereader.batch.dataobject.JobConfiguration;

interface ItemElement {
	public Item accept(EcomVisitor visitor);
}

public class UrlElement implements ItemElement {
	private JobConfiguration configuration;
	public UrlElement(JobConfiguration configuration) {
		this.configuration = configuration;
	}
	public JobConfiguration getConfiguration() {
		return this.configuration;
	}
	public void setConfiguration(JobConfiguration configuration) {
		this.configuration = configuration;
	}
	@Override
	public Item accept(EcomVisitor visitor) {
		return visitor.visit(this);
	}
}