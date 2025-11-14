package com.schoolplus.back.controller;

import com.schoolplus.back.dto.AddressDTO;
import com.schoolplus.back.model.Address;
import com.schoolplus.back.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable("id") String id) {
        return addressService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable("id") String id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return addressService.deleteById(id);
    }
}
