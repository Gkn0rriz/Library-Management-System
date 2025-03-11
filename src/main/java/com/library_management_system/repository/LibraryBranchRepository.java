package com.library_management_system.repository;

import com.library_management_system.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryBranchRepository extends JpaRepository<LibraryBranch,Long> {
}
