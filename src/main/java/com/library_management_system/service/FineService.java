package com.library_management_system.service;

import com.library_management_system.entity.Fine;
import com.library_management_system.entity.Member;
import com.library_management_system.repository.FineRepository;
import com.library_management_system.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FineService {

    private final FineRepository fineRepository;
    private final MemberRepository memberRepository;

    public FineService(FineRepository fineRepository, MemberRepository memberRepository) {
        this.fineRepository = fineRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Fine> getAllFines (Pageable pageable){

        return fineRepository.findAll(pageable);
    }

    public Fine getFineById (Long id){

        return fineRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Fine with id " + id + " not found"));
    }

    public Fine getFineByMemberId (Long memberId){

        return fineRepository.findByMemberId(memberId).orElseThrow(() ->
                new EntityNotFoundException("Fine with member_id " + memberId + " was not found"));
    }

    public Fine createFine (Long memberId, Double fineAmount, String fineReason, String paymentStatus){

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException("Member with id " + memberId + " was not found"));

        Fine fine = new Fine(fineAmount, fineReason, paymentStatus);
        fine.setMember(member);

        return fineRepository.save(fine);
    }

    @Transactional
    public void payFine (Long fineId){

        Fine fine = getFineById(fineId);
        fine.setPaymentStatus("paid");

        fineRepository.save(fine);
    }
}
