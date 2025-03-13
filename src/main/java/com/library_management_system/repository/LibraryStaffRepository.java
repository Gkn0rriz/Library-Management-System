package com.library_management_system.repository;

import com.library_management_system.entity.LibraryStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryStaffRepository extends JpaRepository<LibraryStaff,Long> {

    Optional<LibraryStaff> findByStaffMemberName(String name);
}
