package com.flynn.crypttrade.repository;

import com.flynn.crypttrade.domain.CryptoBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoBasicRepository extends JpaRepository<CryptoBasic, Long> {

    Optional<CryptoBasic> findByExternalId(String externalId);
}
