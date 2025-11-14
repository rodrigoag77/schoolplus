package com.schoolplus.back.repository;

import com.schoolplus.back.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
