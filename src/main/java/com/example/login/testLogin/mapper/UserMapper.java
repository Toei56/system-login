package com.example.login.testLogin.mapper;

import com.example.login.testLogin.controller.request.UserRegisterResponse;
import com.example.login.testLogin.entityModel.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse USER_REGISTER_RESPONSE(User user);
}
