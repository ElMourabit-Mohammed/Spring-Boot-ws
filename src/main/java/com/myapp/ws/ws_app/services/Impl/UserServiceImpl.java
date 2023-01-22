package com.myapp.ws.ws_app.services.Impl;

import com.myapp.ws.ws_app.entities.UserEntity;
import com.myapp.ws.ws_app.repositories.UserRepository;
import com.myapp.ws.ws_app.services.UserService;
import com.myapp.ws.ws_app.shared.Utils;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity checkUser =  userRepository.findByEmail(user.getEmail());
        if (checkUser != null) throw new RuntimeException("User already exists!");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user , userEntity);

        userEntity.setUserId(utils.userIdGenerated());
        userEntity.setEncryptedPassword("Encrypted Password");

        UserEntity newUser = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(newUser , userDto);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail() , userEntity.getEncryptedPassword() , new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;

    }
}
