package com.schoolplus.back.security;

import lombok.*;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;

import com.schoolplus.back.model.User;

@Getter
@Setter
public class UserDetail implements UserDetails {
  private final String id;
  private final String email;
  private final String password;

  public static UserDetail from(User user) {
    if (user == null)
      return null;
    return new UserDetail(user);
  }

  public UserDetail(User user) {
    this.id = user.getId();
    this.email = user.getLogin();
    this.password = user.getPassword();
  }

  @Override
  public Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
    return java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
