package com.myapp.ws.ws_app.services;

import com.myapp.ws.ws_app.entities.UserEntity;
import com.myapp.ws.ws_app.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllAddresses();
}
