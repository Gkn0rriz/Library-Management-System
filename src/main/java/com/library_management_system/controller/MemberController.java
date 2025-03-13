package com.library_management_system.controller;

import com.library_management_system.entity.Member;
import com.library_management_system.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<Page<Member>> getAllMembers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "name") String sortBy,
                                                      @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> members = memberService.getAllMembers(pageable);

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id){

        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Member> getMemberByName(@RequestParam String name){

        return ResponseEntity.ok(memberService.getMemberByName(name));
    }

    @PostMapping
    public ResponseEntity<Member> postMember(@RequestBody Member member){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.addMember(member));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id,
                                               @RequestBody Member member){

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(memberService.updateMember(id, member));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id){

        try {
            memberService.deleteMember(id);
            return ResponseEntity.ok("The member with id " + id + " has been deleted");

        } catch (EntityNotFoundException e){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }
    }

}
