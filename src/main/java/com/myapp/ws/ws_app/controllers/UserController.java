package com.myapp.ws.ws_app.controllers;

import com.myapp.ws.ws_app.exceptions.UserException;
import com.myapp.ws.ws_app.requests.UserRequest;
import com.myapp.ws.ws_app.responses.ErrorMessages;
import com.myapp.ws.ws_app.responses.UserResponse;
import com.myapp.ws.ws_app.services.UserService;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public List<UserResponse> getAllUsers(@RequestParam(value = "page" , defaultValue="1") int page,
                                          @RequestParam(value = "limit" , defaultValue="8") int limit,
                                          @RequestParam(value = "search" , defaultValue="") String search,
                                          @RequestParam(value = "status" , defaultValue="1") int status){
        List<UserResponse> usersResponse = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page , limit , search , status);

        for(UserDto userDto : users) {

            //UserResponse user = new UserResponse();
            //BeanUtils.copyProperties(userDto, user);

            ModelMapper modelMapper = new ModelMapper();
            UserResponse user = modelMapper.map(userDto, UserResponse.class);

            usersResponse.add(user);
        }

        return usersResponse;
    }

    @PostMapping(consumes={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE},
                 produces={MediaType.APPLICATION_XML_VALUE , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest userRequest) throws Exception{
        if(userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userRequest , userDto);

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userRequest , UserDto.class);

        UserDto createUser = userService.createUser(userDto);

//        UserResponse userResponse = new UserResponse();
//        BeanUtils.copyProperties(createUser , userResponse);

        UserResponse userResponse = modelMapper.map(createUser , UserResponse.class);

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
