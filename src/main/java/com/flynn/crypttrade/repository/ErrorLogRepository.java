package com.flynn.crypttrade.repository;

import com.flynn.crypttrade.domain.ErrorLog;
import com.flynn.crypttrade.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

    List<ErrorLog> findAllBySource(String source);
}
