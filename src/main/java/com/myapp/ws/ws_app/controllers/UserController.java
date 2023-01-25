package com.myapp.ws.ws_app.controllers;

import com.myapp.ws.ws_app.exceptions.UserException;
import com.myapp.ws.ws_app.requests.UserRequest;
import com.myapp.ws.ws_app.responses.ErrorMessages;
import com.myapp.ws.ws_app.responses.UserResponse;
import com.myapp.ws.ws_app.services.UserService;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/{id}" , produces= {MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){

        UserDto userDto = userService.getUserByUserId(id);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto , userResponse);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }

    @PostMapping(consumes={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
                 produces={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) throws Exception{
        if(userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest , userDto);

        UserDto createUser = userService.createUser(userDto);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(createUser , userResponse);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
    }
    @PutMapping(path="/{id}" ,
            consumes={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id ,@RequestBody UserRequest userRequest){

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest , userDto);

        UserDto updateUser = userService.updateUser(id , userDto);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(updateUser , userResponse);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
