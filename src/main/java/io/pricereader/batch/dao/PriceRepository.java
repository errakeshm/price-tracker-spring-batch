package io.pricereader.batch.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.pricereader.batch.entities.Price;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long>{
	
}
