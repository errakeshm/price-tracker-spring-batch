package io.pricereader.batch.entities;

public class ProductAddEvent {
	private final Product product;
	public ProductAddEvent(Product product) {
		this.product = product;
	}
	public Product getProduct() {
		return product;
	}
}
