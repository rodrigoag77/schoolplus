package com.schoolplus.back.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.schoolplus.back.repository.UserRepository;
import com.schoolplus.back.security.UserDetail;

@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = repository.findByLogin(email);
    if (user == null)
      throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    return new UserDetail( user );
  }

}
