package com.library_management_system.repository;

import com.library_management_system.entity.LibraryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryTransactionRepository extends JpaRepository<LibraryTransaction,Long> {
}
