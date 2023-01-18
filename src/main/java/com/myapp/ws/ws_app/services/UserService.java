package com.myapp.ws.ws_app.services;

import com.myapp.ws.ws_app.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
