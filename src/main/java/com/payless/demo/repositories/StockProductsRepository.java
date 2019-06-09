package com.payless.demo.repositories;

import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payless.demo.model.StockProducts;

@Repository
@Transactional
public interface StockProductsRepository extends CrudRepository<StockProducts, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="  Select SP FROM StockProducts SP inner join SP.stock S where SP.product.id in (:products) and S.trader.id in (:traders) order by SP.salesprice " )
	List<StockProducts> findProductsInTraders(@Param("products") List<Long> idProducts, @Param("traders") List<Long> idTraders);

	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="  Select SP FROM StockProducts SP inner join SP.stock S where SP.stock.id=:idStock" )
	List<StockProducts> findProductsInStock(@Param("idStock") Long idStock);

}
