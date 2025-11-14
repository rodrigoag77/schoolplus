package com.schoolplus.back.repository;

import com.schoolplus.back.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
