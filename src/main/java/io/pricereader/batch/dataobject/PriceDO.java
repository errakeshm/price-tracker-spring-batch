package io.pricereader.batch.dataobject;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PriceDO {
	private Long productId;
	private Float price;
	private LocalDateTime timestamp;
	public PriceDO(Long productId, Float price, LocalDateTime timestamp) {
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
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
