package com.myapp.ws.ws_app.services.Impl;

import com.myapp.ws.ws_app.entities.UserEntity;
import com.myapp.ws.ws_app.repositories.UserRepository;
import com.myapp.ws.ws_app.services.UserService;
import com.myapp.ws.ws_app.shared.Utils;
import com.myapp.ws.ws_app.shared.dto.AddressDto;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(user , userEntity);

        for(int i=0 ; i<user.getAddresses().size() ; i++){
            AddressDto address = user.getAddresses().get(i);
            address.setUser(user);
            address.setAddressId(utils.stringIdGenerated());

            user.getAddresses().set(i,address);
        }

        user.getContact().setContactId(utils.stringIdGenerated());
        user.getContact().setUser(user);

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity =modelMapper.map(user,UserEntity.class);

        userEntity.setUserId(utils.stringIdGenerated());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity newUser = userRepository.save(userEntity);

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(newUser , userDto);

        UserDto userDto = modelMapper.map(newUser , UserDto.class);

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

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new UsernameNotFoundException(userId);

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUser = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(updatedUser , userDto);

        return userDto;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new UsernameNotFoundException(userId);

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit , String search , int status) {
        if (page>0) page -=1;
        List<UserDto> userDto = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> userPage;
        if(search.isEmpty()) {
            userPage = userRepository.findAllUsers(pageableRequest);
        } else {
            userPage = userRepository.findAllUserByCriteria(pageableRequest ,search , status);
        }

        List<UserEntity> users = userPage.getContent();

        for(UserEntity userEntity : users) {
            //UserDto user = new UserDto();
            //BeanUtils.copyProperties(userEntity, user);

            ModelMapper modelMapper = new ModelMapper();
            UserDto user = modelMapper.map(userEntity, UserDto.class);

            userDto.add(user);
        }
        return userDto;
    }
}
