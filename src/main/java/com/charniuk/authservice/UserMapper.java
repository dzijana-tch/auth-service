package com.charniuk.authservice;

import com.charniuk.authservice.dto.request.UserUpdateRequest;
import com.charniuk.authservice.dto.response.UserResponse;
import com.charniuk.authservice.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING)
public interface UserMapper {

  UserResponse toResponse(User user);

  List<UserResponse> toResponse(List<User> users);

  void updateUserFromRequest(@MappingTarget User user, UserUpdateRequest request);
}