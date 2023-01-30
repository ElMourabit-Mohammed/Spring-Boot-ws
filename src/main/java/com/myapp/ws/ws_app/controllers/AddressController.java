package com.myapp.ws.ws_app.controllers;

import com.myapp.ws.ws_app.responses.AddressResponse;
import com.myapp.ws.ws_app.services.AddressService;
import com.myapp.ws.ws_app.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses() {

        List<AddressDto> addresses = addressService.getAllAddresses();
        Type listType = new TypeToken<List<AddressResponse>>() {}.getType();
        List<AddressResponse> addressResponse = new ModelMapper().map(addresses, listType);

        return new ResponseEntity<List<AddressResponse>>(addressResponse , HttpStatus.OK);
    }
}
