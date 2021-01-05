package io.pricereader.batch.dao;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.pricereader.batch.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	@Query("SELECT p from Product p where p.name=?1")
	Optional<Product> findByName(String name);
}
