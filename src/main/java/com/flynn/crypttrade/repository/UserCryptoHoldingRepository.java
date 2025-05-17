package com.flynn.crypttrade.repository;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserCryptoHoldingRepository extends JpaRepository<UserCryptoHolding, Long> {
    List<UserCryptoHolding> findByUserId(Long userId);

    List<UserCryptoHolding> findAllByUserAndCryptoBasic(User user, CryptoBasic cryptoBasic);
    Optional<UserCryptoHolding> findByUserAndCryptoBasic(User user, CryptoBasic crypto);

}
