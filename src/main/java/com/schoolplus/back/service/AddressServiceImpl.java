package com.schoolplus.back.service;

import com.schoolplus.back.dto.AddressDTO;
import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.model.Address;
import com.schoolplus.back.model.City;
import com.schoolplus.back.repository.AddressRepository;
import com.schoolplus.back.repository.CityRepository;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl extends BaseDTOServiceImpl<Address, AddressDTO, String> implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    protected JpaRepository<Address, String> getRepository() {
        return addressRepository;
    }

    @Override
    protected AddressDTO toDTO(Address entity) {
        return new AddressDTO(entity);
    }

    @Override
    protected Address toEntity(AddressDTO dto) {
        // Implementação básica - pode ser estendida conforme necessário
        throw new UnsupportedOperationException("Conversão de DTO para entidade não suportada");
    }

    @Override
    public ResponseEntity<List<AddressDTO>> findAll() {
        try {
            List<Address> addresses = addressRepository.findAll();
            List<AddressDTO> dtos = addresses.stream()
                    .map(this::toDTO)
                    .toList();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            throw new ServiceException("Error listing addresses: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<AddressDTO> save(@NonNull Address address) {
        try {
            Address savedAddress = addressRepository.save(address);
            return ResponseEntity.ok(toDTO(savedAddress));
        } catch (Exception e) {
            throw new ServiceException("Error saving address: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ResponseEntity<AddressDTO> update(@NonNull String id, @NonNull Address address) {
        try {

            Address existing = addressRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Address not found"));

            updateIfNotNullOrEmpty(existing, address);
            updateAssociatedCity(existing, address);

            Address updated = addressRepository.save(existing);
            return ResponseEntity.ok(toDTO(updated));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error updating address: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> deleteById(@NonNull String id) {
        try {
            if (!addressRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            addressRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ServiceException("Error deleting address: " + e.getMessage());
        }
    }

    /**
     * Atualiza a cidade associada ao endereço
     * 
     * @param existing o endereço existente
     * @param address  os novos dados do endereço
     */
    @SuppressWarnings("null")
    private void updateAssociatedCity(@NonNull Address existing, @NonNull Address address) {
        if (address.getCity() != null && address.getCity().getId() != null) {
            String cityId = address.getCity().getId();
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new ServiceException("City not found"));
            existing.setCity(city);
        }
    }
}
