package com.library_management_system.service;

import com.library_management_system.entity.Member;
import com.library_management_system.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }


    public Page<Member> getAllMembers(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    public Member getMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Member not found"));
    }

    public Member getMemberByName(String name){
        return memberRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Member does not exist"));
    }

    public Member addMember(Member member){
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member member){
        Member existingMember = memberRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Member doesn't exist"));

        modelMapper.map(member, existingMember);

        return memberRepository.save(existingMember);

    }

    @Transactional
    public void deleteMember(Long id){
        if (!memberRepository.existsById(id))
            throw new EntityNotFoundException("Member doesn't exist");

        memberRepository.deleteById(id);
    }
}
