package io.pricereader.batch.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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
	private Timestamp timestamp;
	public PriceKey() {
	}
	public PriceKey(Long productId, Timestamp timestamp) {
		this.productId = productId;
		this.timestamp = timestamp;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
