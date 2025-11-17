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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.exception.UserAlreadyExistsException;

import lombok.NonNull;

import java.util.List;

@Service
public class MemberServiceImpl extends BaseDTOServiceImpl<Member, MemberDTO, String> implements MemberService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected JpaRepository<Member, String> getRepository() {
    return memberRepository;
  }

  @Override
  protected MemberDTO toDTO(Member entity) {
    return new MemberDTO(entity);
  }

  @Override
  public ResponseEntity<MemberDTO> getById(@NonNull String id) {
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
  public ResponseEntity<List<MemberDTO>> getAll() {
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

      updateNotNullEmpty(existingMember, member);

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
    updateNotNullEmpty(user, member.getUser());

    String login = extractLogin(member);
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  private Address createAndPersistAddress(Member member) {
    Address address = new Address();
    address.setId(member.getAddress().getId());
    updateNotNullEmpty(address, member.getAddress());

    return addressRepository.save(address);
  }

  private void updateUserData(Member existingMember, Member memberUpdate) {
    User user = new User(existingMember.getUser());
    updateNotNullEmpty(user, memberUpdate.getUser());

    String login = extractLogin(memberUpdate);
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);
    existingMember.setUser(savedUser);
  }

  private void updateAddressData(Member existingMember, Member memberUpdate) {
    Address address = new Address(existingMember.getAddress());
    address.setId(memberUpdate.getAddress().getId());
    updateNotNullEmpty(address, memberUpdate.getAddress());

    Address savedAddress = addressRepository.save(address);
    existingMember.setAddress(savedAddress);
  }

  private void updateNotNullEmpty(Object target, Object source) {
    try {
      java.lang.reflect.Field[] fields = target.getClass().getDeclaredFields();
      for (java.lang.reflect.Field field : fields) {
        if (field.getName().equalsIgnoreCase("id"))
          continue;

        field.setAccessible(true);
        Object sourceValue = field.get(source);
        Object targetValue = field.get(target);

        if (sourceValue != null) {
          if (isComplexObject(sourceValue) && targetValue != null)
            updateNotNullEmpty(targetValue, sourceValue);
          else
            field.set(target, sourceValue);
        }
      }
    } catch (IllegalAccessException e) {
      throw new ServiceException("Error updating fields: " + e.getMessage());
    }
  }

  private boolean isComplexObject(Object obj) {
    Class<?> clazz = obj.getClass();
    return !clazz.isPrimitive()
        && !(obj instanceof String)
        && !(obj instanceof Number)
        && !(obj instanceof Boolean)
        && !(obj instanceof java.sql.Timestamp)
        && !(obj instanceof java.util.Date)
        && !(obj instanceof java.util.List)
        && !(obj instanceof java.util.Map)
        && !(obj instanceof Enum);
  }
}
