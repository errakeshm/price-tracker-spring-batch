package io.pricereader.batch.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.dataobject.PriceDO;
import io.pricereader.batch.dataobject.ProductDO;
import io.pricereader.batch.handlers.ProductHandler;

@RestController
public class ProductController {
	
	@Autowired
	private ProductHandler productHandler;
	
	@PostMapping(value = "/product", consumes= {"application/json"})
	public ResponseEntity<Long> addProduct(@RequestBody ProductDO product) {
		return ResponseEntity.ok(this.productHandler.addProduct(product));
	}
	
	@PostMapping(value = "/product/price", consumes= {"application/json"})
	public ResponseEntity<Boolean> addPrice(@RequestBody PriceDO price) {
		this.productHandler.addPrice(price);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping(value = "/product/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDO> getProductById(@PathVariable Long id) {
		CommonConstants.LOGGER.info("Inside getProductbyId");
		return ResponseEntity.ok(this.productHandler.getProductById(id));
	}
	
	@GetMapping(value = "/product/price/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PriceDO>> getPriceByProductId(@PathVariable Long id) {
		return ResponseEntity.ok(this.productHandler.getPriceListByProductId(id));
	}
}
