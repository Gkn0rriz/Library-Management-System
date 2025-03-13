package com.library_management_system.controller;

import com.library_management_system.entity.Book;
import com.library_management_system.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "book_name") String sortBy,
                                                  @RequestParam(defaultValue = "true") boolean ascending)
    {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        return ResponseEntity.ok(bookService.fetchAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.fetchBookById(id));
    }

    @GetMapping("/findByAuthor")
    public ResponseEntity<Page<Book>> getBookByAuthor(@RequestParam String author,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "book_name") String sortBy,
                                                      @RequestParam(defaultValue = "true") boolean ascending)
    {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        return ResponseEntity.ok(bookService.fetchBookByAuthor(author,pageable));

    }

    @GetMapping("/findByTitle")
    public ResponseEntity<List<Book>> getBookByTitle(@RequestParam String title){
        return ResponseEntity.ok(bookService.fetchBookByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book book){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bookService.addBook(book));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bookService.updateBook(id, book));
    }


    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){

        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book with id " + id + " was deleted successfully");

    }


}
