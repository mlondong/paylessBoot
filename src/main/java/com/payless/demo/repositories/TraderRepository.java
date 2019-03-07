package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Address;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;




@Repository
@Transactional
public interface TraderRepository extends BaseUserRepository<Trader>{

	Trader findByCuit(long cuit);
	List<Trader> findByStock(Stock stock);

}
