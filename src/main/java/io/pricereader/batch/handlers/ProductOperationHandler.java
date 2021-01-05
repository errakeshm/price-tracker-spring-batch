package io.pricereader.batch.handlers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import io.pricereader.batch.constants.CommonConstants;
import io.pricereader.batch.entities.Product;
import io.pricereader.batch.entities.ProductAddEvent;

@Service
public class ProductOperationHandler {
	// Listen for Product Add Events (save)
	@Async
	@TransactionalEventListener
	public void handleProductAddEvent(ProductAddEvent event) {
		Product product = event.getProduct();
		CommonConstants.LOGGER.info("Printing Product information : " + product.toString());
	}
}
