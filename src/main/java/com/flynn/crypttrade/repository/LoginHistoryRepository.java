package com.flynn.crypttrade.repository;


import com.flynn.crypttrade.domain.LoginHistory;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findAllByUserId(Long userId);

}
