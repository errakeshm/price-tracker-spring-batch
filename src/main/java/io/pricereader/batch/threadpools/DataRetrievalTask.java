package io.pricereader.batch.threadpools;

import java.util.concurrent.Callable;

import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.parser.EcomVisitor;
import io.pricereader.batch.parser.EcomVisitorImpl;
import io.pricereader.batch.parser.Item;
import io.pricereader.batch.parser.UrlElement;

public class DataRetrievalTask implements Callable<Item>{

	private UrlElement url;
	
	public DataRetrievalTask(UrlElement url) {
		this.url = url;
	}
	
	@Override
	public Item call() throws Exception {
		EcomVisitor ecomVisitor = new EcomVisitorImpl();
		Item productData = url.accept(ecomVisitor);
		CommonConstants.LOGGER.info(productData.toString());
		return productData;
	}
}
