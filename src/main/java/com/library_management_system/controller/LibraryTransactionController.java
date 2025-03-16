package com.library_management_system.controller;

import com.library_management_system.entity.LibraryTransaction;
import com.library_management_system.service.LibraryTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/transactions")
public class LibraryTransactionController {

    private final LibraryTransactionService transactionService;

    public LibraryTransactionController(LibraryTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Page<LibraryTransaction>> getAllTransactions (@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                                        @RequestParam(defaultValue = "true") boolean ascending)
    {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LibraryTransaction> transactionPage = transactionService.getAllTransactions(pageable);

        return ResponseEntity.ok(transactionPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryTransaction> getTransactionById (@PathVariable Long id){

        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/findByMember/{memberId}")
    public ResponseEntity<LibraryTransaction> getTransactionByMemberId (@PathVariable Long memberId){

        return ResponseEntity.ok(transactionService.getTransactionByMemberId(memberId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<LibraryTransaction> checkOutBook (@RequestParam Long memberId,
                                                            @RequestParam long staffId,
                                                            @RequestParam Long bookId){

        LibraryTransaction savedTransaction = transactionService.checkOutBook(memberId, staffId, bookId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedTransaction);
    }

    @PostMapping("/returnBook")
    public ResponseEntity<LibraryTransaction> returnBook (@RequestParam Long transactionId){

        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }




}
