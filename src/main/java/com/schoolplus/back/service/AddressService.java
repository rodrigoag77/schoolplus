package com.schoolplus.back.service;

import com.schoolplus.back.dto.AddressDTO;
import com.schoolplus.back.model.Address;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<AddressDTO> findById(String id);

    ResponseEntity<List<AddressDTO>> findAll();

    ResponseEntity<AddressDTO> save(Address address);

    ResponseEntity<Void> deleteById(String id);

    ResponseEntity<AddressDTO> update(String id, Address address);
}
