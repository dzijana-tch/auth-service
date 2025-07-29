package com.charniuk.authservice.feign;

import com.charniuk.authservice.config.FeignConfiguration;
import com.charniuk.authservice.dto.request.UserChangeNotificationRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "notification-service",
    url = "${notification-service.url}",
    configuration = FeignConfiguration.class
)
public interface NotificationClient {

  @PostMapping("api/v1/notification/email/user-changes")
  ResponseEntity<Void> notifyAdminsAboutUserChange(
      @RequestBody @Valid UserChangeNotificationRequest request);
}
