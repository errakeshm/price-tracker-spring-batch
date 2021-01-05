package io.pricereader.batch.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PriceKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "productId", nullable = false)
	private Long productId;
	
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	public PriceKey() {
	}
	public PriceKey(Long productId, LocalDateTime timestamp) {
		this.productId = productId;
		this.timestamp = timestamp;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
