package io.pricereader.batch.entities;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Price {
	@EmbeddedId
	private PriceKey priceKey;
	private Float value;
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public void setPriceKey(PriceKey priceKey) {
		this.priceKey = priceKey;
	}
	public PriceKey getPriceKey() {
		return priceKey;
	}
}
