package com.library_management_system.repository;

import com.library_management_system.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine,Long> {

    @Query("select * from ")
    Optional<Fine> findByMemberId (Long memberId);
}
