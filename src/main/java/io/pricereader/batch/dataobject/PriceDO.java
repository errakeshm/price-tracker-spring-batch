package io.pricereader.batch.dataobject;

import java.sql.Timestamp;

public class PriceDO {
	private Long productId;
	private Float price;
	private Timestamp timestamp;
	public PriceDO(Long productId, Float price, Timestamp timestamp) {
		this.productId = productId;
		this.price = price;
		this.timestamp = timestamp;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
