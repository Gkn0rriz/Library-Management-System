package com.library_management_system.repository;

import com.library_management_system.entity.LibraryStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryStaffRepository extends JpaRepository<LibraryStaff,Long> {
}
