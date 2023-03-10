package com.myapp.ws.ws_app.controllers;

import com.myapp.ws.ws_app.requests.AddressRequest;
import com.myapp.ws.ws_app.responses.AddressResponse;
import com.myapp.ws.ws_app.services.AddressService;
import com.myapp.ws.ws_app.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(Principal principal) {

        List<AddressDto> addresses = addressService.getAllAddresses(principal.getName());
        Type listType = new TypeToken<List<AddressResponse>>() {}.getType();
        List<AddressResponse> addressResponse = new ModelMapper().map(addresses, listType);

        return new ResponseEntity<List<AddressResponse>>(addressResponse , HttpStatus.OK);
    }

    @PostMapping(
            consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AddressResponse> StoreAddresse(@RequestBody AddressRequest addressRequest, Principal principal) {

        ModelMapper modelMapper = new ModelMapper();

        AddressDto addressDto = modelMapper.map(addressRequest, AddressDto.class);
        AddressDto createAddress = addressService.createAddress(addressDto, principal.getName());

        AddressResponse newAddress = modelMapper.map(createAddress, AddressResponse.class);

        return new ResponseEntity<AddressResponse>(newAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<AddressResponse> getOneAddresse(@PathVariable(name="id") String addressId) {

        AddressDto addressDto = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();

        AddressResponse addressResponse = modelMapper.map(addressDto, AddressResponse.class);

        return new ResponseEntity<AddressResponse>(addressResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatreAddresse(@PathVariable(name="id") String addressId) {
        return new ResponseEntity<>("update addresses", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddresse(@PathVariable(name="id") String addressId) {

        addressService.deleteAddress(addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
