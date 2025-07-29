package com.charniuk.authservice.service.impl;

import com.charniuk.authservice.dto.request.UserChangeNotificationRequest;
import com.charniuk.authservice.enums.UserAction;
import com.charniuk.authservice.feign.NotificationClient;
import com.charniuk.authservice.model.User;
import com.charniuk.authservice.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationClient notificationClient;

  @Override
  public void notifyAdmins(UserAction action, User user, List<String> adminEmails) {

    UserChangeNotificationRequest request = UserChangeNotificationRequest.builder()
        .action(action)
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword().substring(6))
        .adminEmails(adminEmails)
        .build();

    try {
      notificationClient.notifyAdminsAboutUserChange(request);
    } catch (Exception e) {
      log.error("Ошибка при отправке уведомления администратору об аккаунте {}", user.getUserId());
    }
  }
}
