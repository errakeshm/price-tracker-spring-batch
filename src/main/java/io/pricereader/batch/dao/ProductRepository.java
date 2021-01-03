package io.pricereader.batch.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.pricereader.batch.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	Optional<Product> findByName(String name);
}
