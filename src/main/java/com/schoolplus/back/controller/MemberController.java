package com.schoolplus.back.controller;

import com.schoolplus.back.dto.MemberDTO;
import com.schoolplus.back.model.Member;
import com.schoolplus.back.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return memberService.deleteById(id);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getById(@PathVariable("id") String id) {
        return memberService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Member member) {
        return memberService.create(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> update(@PathVariable("id") String id, @RequestBody Member member) {
        return memberService.update(id, member);
    }
}
