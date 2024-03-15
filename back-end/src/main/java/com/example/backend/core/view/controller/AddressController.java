package com.example.backend.core.view.controller;

import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/get-all-address")
    public ResponseEntity<?> getAllAddressByCustomer(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.getAllAddress(addressDTO));
    }
    @PostMapping("/get-address")
    public ResponseEntity<?> getAddressByCustomer(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.getAddress(addressDTO));
    }

    @PostMapping("/create-address")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.save(addressDTO));
    }

    @PostMapping("/update-address/config")
    public ResponseEntity<?> udpateAddressConfig(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.updateConfig(addressDTO));
    }
    @GetMapping("/detail-address/{idAddress}")
    public ResponseEntity<?> detailAddress(@PathVariable(name = "idAddress") Long idAddress){
        return ResponseEntity.ok(addressService.detailAddressConfig(idAddress));
    }
}
