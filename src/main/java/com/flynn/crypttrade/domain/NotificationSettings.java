package com.flynn.crypttrade.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_settings")
public class NotificationSettings {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean dailyReport;
    private Boolean weeklyReport;
    private LocalTime preferredTime;
}
