package io.pricereader.batch.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class Product extends AbstractAggregateRoot<Product> implements Serializable {
	private static final long serialVersionUID = -8782593565874087701L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	private String category;
	private String name;
	@Column(length=400)
	private String url;
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="productId")
	private List<Price> price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Price> getPrice() {
		return price;
	}
	public void setPrice(List<Price> price) {
		this.price = price;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	
	// Abstract Aggregate root (to register a domain event when any save action is carried out)
	public void registerProductAddEvent() {
		// Register an event
		registerEvent(new ProductAddEvent(this));
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", category=" + category + ", name=" + name + ", url=" + url
				+ ", price=" + price + "]";
	}
}
