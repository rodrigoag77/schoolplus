package com.schoolplus.back.service;

import com.schoolplus.back.model.Member;
import com.schoolplus.back.dto.MemberDTO;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MemberService {
  ResponseEntity<Void> deleteById(String id);

  ResponseEntity<MemberDTO> findById(String id);

  ResponseEntity<List<MemberDTO>> findAll();

  ResponseEntity<MemberDTO> create(Member member);

  ResponseEntity<MemberDTO> update(String id, Member member);
}
