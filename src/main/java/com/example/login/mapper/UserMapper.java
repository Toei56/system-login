package com.example.login.mapper;

import com.example.login.controller.request.UserProfile;
import com.example.login.controller.request.UserRegisterResponse;
import com.example.login.entityModel.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse USER_REGISTER_RESPONSE(User user);

    UserProfile toUserProfile(User user);
}
