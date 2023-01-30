package com.myapp.ws.ws_app.services.Impl;

import com.myapp.ws.ws_app.entities.AddressEntity;
import com.myapp.ws.ws_app.repositories.AddressRepository;
import com.myapp.ws.ws_app.services.AddressService;
import com.myapp.ws.ws_app.shared.dto.AddressDto;
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
    @Override
    public List<AddressDto> getAllAddresses() {
        List<AddressEntity> addresses = (List<AddressEntity>) addressRepository.findAll();

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
}
