package com.myapp.ws.ws_app.controllers;

import com.myapp.ws.ws_app.repositories.UserRepository;
import com.myapp.ws.ws_app.requests.UserRequest;
import com.myapp.ws.ws_app.responses.UserResponse;
import com.myapp.ws.ws_app.services.UserService;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserResponse addUser(@RequestBody UserRequest userRequest){

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest , userDto);

        UserDto createUser = userService.createUser(userDto);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(createUser , userResponse);

        return  userResponse;
    }
    @PutMapping
    public String updateUser(){
        return "Update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "Delete user was called";
    }
}
