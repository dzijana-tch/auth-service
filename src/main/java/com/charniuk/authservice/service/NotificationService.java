package com.charniuk.authservice.service;

import com.charniuk.authservice.enums.UserAction;
import com.charniuk.authservice.model.User;
import java.util.List;

public interface NotificationService {

  /**
   * Уведомление администраторов о действиях с аккаунтом
   */
  void notifyAdmins(UserAction action, User user, List<String> adminEmails);
}
