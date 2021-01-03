package io.pricereader.batch.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.pricereader.batch.dataobject.PriceDO;
import io.pricereader.batch.dataobject.ProductDO;
import io.pricereader.batch.entities.Price;
import io.pricereader.batch.entities.PriceKey;
import io.pricereader.batch.entities.Product;

@Service
public class ProductDAO {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Transactional
	public Long addProduct(ProductDO product) {
		Product productEntity = new Product();
		productEntity.setName(product.getName());
		productEntity.setCategory(product.getCategory());
		productEntity.setUrl(product.getUrl());
		Product savedProduct = this.productRepository.save(productEntity);
		return savedProduct.getProductId();
	}
	
	public ProductDO getProductById(Long id) {
		Optional<Product> product = this.productRepository.findById(id);
		ProductDO productDO = null;
		if(product.isPresent()) {
			Product result =  product.get();
			productDO = new ProductDO();
			productDO.setName(result.getName());
			productDO.setCategory(result.getCategory());
			productDO.setUrl(result.getUrl());
		}
		return productDO;
	}
	
	public List<PriceDO> getPriceListById(Long id, int size){
		Optional<Product> productOptional = this.productRepository.findById(id);
		if(productOptional.isPresent()) {
			Product product = productOptional.get();
			return product.getPrice().stream().limit(size).map(price->{
				return new PriceDO(price.getPriceKey().getProductId(), price.getValue(), 
						price.getPriceKey().getTimestamp());
			}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	public void addPrice(PriceDO price) {
		Price priceEntity = new Price();
		priceEntity.setPriceKey(new PriceKey(price.getProductId(), price.getTimestamp()));
		priceEntity.setValue(price.getPrice());
		this.priceRepository.save(priceEntity);
	}
}
