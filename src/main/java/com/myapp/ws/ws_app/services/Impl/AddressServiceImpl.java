package com.myapp.ws.ws_app.services.Impl;

import com.myapp.ws.ws_app.entities.AddressEntity;
import com.myapp.ws.ws_app.entities.UserEntity;
import com.myapp.ws.ws_app.repositories.AddressRepository;
import com.myapp.ws.ws_app.repositories.UserRepository;
import com.myapp.ws.ws_app.services.AddressService;
import com.myapp.ws.ws_app.shared.Utils;
import com.myapp.ws.ws_app.shared.dto.AddressDto;
import com.myapp.ws.ws_app.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
//import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
     AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils util;
    @Override
    public List<AddressDto> getAllAddresses(String email) {

        UserEntity currentUser = userRepository.findByEmail(email);

        List<AddressEntity> addresses = currentUser.getAdmin() == true ? (List<AddressEntity>) addressRepository.findAll() : addressRepository.findByUser(currentUser);

        //List<AddressEntity> addresses = (List<AddressEntity>) addressRepository.findAll();

//        List<AddressDto> addressDto = new ArrayList<>();
//        for(AddressEntity addressEntity : addresses){
//            ModelMapper modelMapper = new ModelMapper();
//            AddressDto address = modelMapper.map(addressEntity , AddressDto.class);
//
//            addressDto.add(address);
//        }

        Type listType = new TypeToken<List<AddressDto>>() {}.getType();
        List<AddressDto> addressesDto = new ModelMapper().map(addresses, listType);

        return addressesDto;
    }

    @Override
    public AddressDto createAddress(AddressDto address, String email) {

        UserEntity currentUser = userRepository.findByEmail(email);

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(currentUser, UserDto.class);

        address.setAddressId(util.stringIdGenerated());
        address.setUser(userDto);

        AddressEntity addressEntity = modelMapper.map(address, AddressEntity.class);

        AddressEntity newAddress = addressRepository.save(addressEntity);

        AddressDto addressDto = modelMapper.map(newAddress, AddressDto.class);

        return addressDto;
    }

    @Override
    public AddressDto getAddress(String addressId) {

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        ModelMapper modelMapper = new ModelMapper();

        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);

        return addressDto;
    }

    @Override
    public void deleteAddress(String addressId) {

        AddressEntity address = addressRepository.findByAddressId(addressId);

        if(address == null) throw new RuntimeException("Address not found");

        addressRepository.delete(address);

    }
}
