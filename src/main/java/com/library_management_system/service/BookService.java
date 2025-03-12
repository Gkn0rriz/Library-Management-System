package com.library_management_system.service;

import com.library_management_system.entity.Book;
import com.library_management_system.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Book> fetchAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Book fetchBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Book> fetchBookByTitle(String title ){
        return bookRepository.findByBookName(title);
    }

    public Page<Book> fetchBookByAuthor(String author, Pageable pageable){
        return bookRepository.findByBookAuthor(author, pageable);
    }

    public Boolean checkBookAvailability(Long id){
        return bookRepository.existsById(id);
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book){
        Book existingBook = bookRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Book not found"));

        modelMapper.map(book,existingBook);

        return bookRepository.save(existingBook);

    }

    @Transactional
    public void deleteBookById(Long id){

        if (!bookRepository.existsById(id))
            throw new EntityNotFoundException("Book not found");

        bookRepository.deleteById(id);
    }


}
