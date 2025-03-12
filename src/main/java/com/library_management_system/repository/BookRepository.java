package com.library_management_system.repository;

import com.library_management_system.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByBookName(String title);

    Page<Book> findByBookAuthor(String author, Pageable pageable);

}
