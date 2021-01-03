package io.pricereader.batch.parser;

import java.io.IOException;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.pricereader.batch.constants.ApplicationConstants;
import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.helpers.NumberHelper;

public class EcomVisitorImpl implements EcomVisitor {
	public Item visit(UrlElement element) {
		Document document;
		try {
			
			document = Jsoup.connect(element.getConfiguration().getUrl()).get();
			Float price = null;
			String name = null;
			if(ApplicationConstants.JobName.flipkart.equals(element.getConfiguration().getJobName())) {
				price = NumberHelper.getCurrencyValue(document.getElementsByClass("_30jeq3 _16Jk6d").text());
				name = document.getElementsByClass("B_NuCI").text();
			} else if(ApplicationConstants.JobName.amazon.equals(element.getConfiguration().getJobName())){
				Element priceElement = document.getElementById("priceblock_dealprice");
				Element nameElement = document.getElementById("productTitle");
				if(priceElement != null) {
					price = NumberHelper.getCurrencyValue(priceElement.text());
				} else
					CommonConstants.LOGGER.debug("No price element found");
				
				if(nameElement != null) {
					name = nameElement.text().trim();
				} else
					CommonConstants.LOGGER.debug("No Name element found");
			}
			
			return Item.ItemBuilder.getInstance()
					.setName(name)
					.setPrice(price!=null ? price : -1)
					.setRecordedTs(LocalDateTime.now())
					.build();
		} catch (IOException e) {
			CommonConstants.LOGGER.error(e.getMessage());
		}
		return null;
	}
}
