package com.zemoga.portfolio.api.repository;

import com.zemoga.portfolio.api.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {

}
