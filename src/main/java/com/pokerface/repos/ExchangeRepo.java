package com.pokerface.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokerface.model.Exchange;

public interface ExchangeRepo extends JpaRepository<Exchange, Long> {

}
