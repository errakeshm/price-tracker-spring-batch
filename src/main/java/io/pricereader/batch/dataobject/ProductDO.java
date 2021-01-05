package io.pricereader.batch.dataobject;

public class ProductDO {
	private Long productId;
	private String name;
	private String category;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductId() {
		return productId;
	}
	@Override
	public String toString() {
		return "ProductDO [productId=" + productId + ", name=" + name + ", category=" + category + ", url=" + url + "]";
	}
}
