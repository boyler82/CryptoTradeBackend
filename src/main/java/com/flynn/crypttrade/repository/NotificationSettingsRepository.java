package com.flynn.crypttrade.repository;


import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
    Optional<NotificationSettings> findByUserId(Long userId);

    Optional<NotificationSettings> findByUser(User user);
}
