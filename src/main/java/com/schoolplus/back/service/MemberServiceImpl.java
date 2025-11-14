package com.schoolplus.back.service;

import com.schoolplus.back.model.Address;
import com.schoolplus.back.model.Member;
import com.schoolplus.back.model.User;
import com.schoolplus.back.dto.MemberDTO;
import com.schoolplus.back.repository.AddressRepository;
import com.schoolplus.back.repository.MemberRepository;
import com.schoolplus.back.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.exception.UserAlreadyExistsException;

import lombok.NonNull;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public ResponseEntity<Void> deleteById(@NonNull String id) {
    try {
      if (!memberRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
      }
      memberRepository.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw new ServiceException("Error deleting member: " + e.getMessage());
    }
  }

  @Override
  public ResponseEntity<MemberDTO> findById(@NonNull String id) {
    try {
      return memberRepository.findById(id)
          .map(MemberDTO::new)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      throw new ServiceException("Error finding member: " + e.getMessage());
    }
  }

  @Override
  public ResponseEntity<List<MemberDTO>> findAll() {
    try {
      List<MemberDTO> members = memberRepository.findAll()
          .stream()
          .map(MemberDTO::new)
          .toList();
      return ResponseEntity.ok(members);
    } catch (Exception e) {
      throw new ServiceException("Error listing members: " + e.getMessage());
    }
  }

  @Override
  @Transactional
  @SuppressWarnings("null")
  public ResponseEntity<MemberDTO> update(@NonNull String id, @NonNull Member member) {
    try {
      validateUserLogin(member);

      Member existingMember = memberRepository.findById(id)
          .orElseThrow(() -> new ServiceException("Member not found"));

      if (member.getUser() != null) {
        updateUserData(existingMember, member);
      }

      if (member.getAddress() != null) {
        updateAddressData(existingMember, member);
      }

      Member updatedMember = memberRepository.save(existingMember);
      Member result = memberRepository.findById(updatedMember.getId())
          .orElseThrow(() -> new ServiceException("Failed to retrieve updated member"));

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MemberDTO(result));
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("Error updating member: " + e.getMessage());
    }
  }

  @Override
  @Transactional
  @SuppressWarnings("null")
  public ResponseEntity<MemberDTO> create(@NonNull Member member) {
    try {
      validateUserLogin(member);

      User user = createAndPersistUser(member);
      Address address = createAndPersistAddress(member);

      Member newMember = new Member(member);
      newMember.setUser(user);
      newMember.setAddress(address);

      Member createdMember = memberRepository.save(newMember);
      Member result = memberRepository.findById(createdMember.getId())
          .orElseThrow(() -> new ServiceException("Failed to retrieve created member"));

      return ResponseEntity.status(HttpStatus.CREATED).body(new MemberDTO(result));
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("Error creating member: " + e.getMessage());
    }
  }

  private void validateUserLogin(Member member) {
    if (member.getUser() == null) {
      return;
    }

    String login = extractLogin(member);
    User existingUser = userRepository.findByLogin(login);

    if (existingUser != null && existingUser.getId() != null) {
      throw new UserAlreadyExistsException();
    }
  }

  private String extractLogin(Member member) {
    String login = member.getUser().getLogin();
    return (login == null || login.isEmpty()) ? member.getEmail() : login;
  }

  private User createAndPersistUser(Member member) {
    User user = new User(member.getUser());
    updateIfNotNullOrEmpty(user, member.getUser());

    String login = extractLogin(member);
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  private Address createAndPersistAddress(Member member) {
    Address address = new Address();
    address.setId(member.getAddress().getId());
    updateIfNotNullOrEmpty(address, member.getAddress());

    return addressRepository.save(address);
  }

  private void updateUserData(Member existingMember, Member memberUpdate) {
    User user = new User(existingMember.getUser());
    updateIfNotNullOrEmpty(user, memberUpdate.getUser());

    String login = extractLogin(memberUpdate);
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);
    existingMember.setUser(savedUser);
  }

  private void updateAddressData(Member existingMember, Member memberUpdate) {
    Address address = new Address(existingMember.getAddress());
    address.setId(memberUpdate.getAddress().getId());
    updateIfNotNullOrEmpty(address, memberUpdate.getAddress());

    Address savedAddress = addressRepository.save(address);
    existingMember.setAddress(savedAddress);
  }

  private void updateIfNotNullOrEmpty(Object target, Object source) {
    try {
      java.lang.reflect.Field[] fields = target.getClass().getDeclaredFields();
      for (java.lang.reflect.Field field : fields) {
        if (field.getName().equalsIgnoreCase("id")) {
          continue;
        }
        field.setAccessible(true);
        Object value = field.get(source);
        if (value != null) {
          field.set(target, value);
        }
      }
    } catch (IllegalAccessException e) {
      throw new ServiceException("Error updating fields: " + e.getMessage());
    }
  }

}
