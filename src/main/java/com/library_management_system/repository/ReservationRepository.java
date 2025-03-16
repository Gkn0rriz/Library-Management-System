package com.library_management_system.repository;

import com.library_management_system.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Page<Reservation> findByMemberId (Long memberId, Pageable pageable);
}
