package com.library_management_system.repository;

import com.library_management_system.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine,Long> {
}
