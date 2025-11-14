package com.schoolplus.back.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.schoolplus.back.repository.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    var jwtToken = getToken(request);
    try {
      if (jwtToken != null) {
        var subject = tokenService.getSubject(jwtToken);
        var user = userRepository.findByLogin(subject);
        var userDetail = UserDetail.from(user);
        if (userDetail != null) {
          var authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (com.schoolplus.back.exception.InvalidTokenException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write("{\"message\":\"" + ex.getMessage() + "\"}");
    }
  }

  private String getToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader != null)
      return authHeader.replace("Bearer ", "");
    return null;
  }
}
