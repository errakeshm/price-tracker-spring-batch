package io.pricereader.batch.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import io.pricereader.batch.dao.ProductDAO;
import io.pricereader.batch.dataobject.Category;
import io.pricereader.batch.dataobject.PriceDO;
import io.pricereader.batch.dataobject.ProductDO;

@Service
public class ProductHandler {
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ApplicationContext applicationContext;

	public Long addProduct(ProductDO product) {
		Long productId =  this.productDAO.addProduct(product);
		Category category = (Category) this.applicationContext.getBean(product.getCategory());
		category.getUrls().put(product.getName(), product.getUrl());
		return productId;
	}
	
	public ProductDO getProductById(Long id) {
		return this.productDAO.getProductById(id);
	}
	
	public List<PriceDO> getPriceListByProductId(Long id){
		return this.productDAO.getPriceListById(id, 10);
	}
	
	public void addPrice(PriceDO price) {
		this.productDAO.addPrice(price);
	}
}
