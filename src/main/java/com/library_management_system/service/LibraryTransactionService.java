package com.library_management_system.service;

import com.library_management_system.entity.Book;
import com.library_management_system.entity.LibraryStaff;
import com.library_management_system.entity.LibraryTransaction;
import com.library_management_system.entity.Member;
import com.library_management_system.repository.BookRepository;
import com.library_management_system.repository.LibraryStaffRepository;
import com.library_management_system.repository.LibraryTransactionRepository;
import com.library_management_system.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LibraryTransactionService {

    private final LibraryTransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LibraryStaffRepository staffRepository;
    private final ModelMapper modelMapper;
    private final static Double MAX_FINE = 5000d;
    private final static int MAX_BOOK_DAYS = 5;

    public LibraryTransactionService(LibraryTransactionRepository transactionRepository, BookRepository bookRepository, MemberRepository memberRepository, LibraryStaffRepository staffRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    public Page<LibraryTransaction> getAllTransactions (Pageable pageable){
        return transactionRepository.findAll(pageable);
    }

    public LibraryTransaction getTransactionById (Long id){

        return transactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transaction with id " + id + " not found"));
    }

    public LibraryTransaction getTransactionByMemberId (Long memberId){

        return transactionRepository.findByMember(memberId).orElseThrow(() ->
                new EntityNotFoundException("Transaction with member-id " + memberId + " was not found"));

    }

    @Transactional
    //create a book transaction
    public LibraryTransaction checkOutBook(Long memberId, long staffId, Long bookId){

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException("Member with id " + memberId + " was not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " was not found"));

        LibraryStaff staff = staffRepository.findById(staffId).orElseThrow(() ->
                new EntityNotFoundException("Staff member with id " + staffId + " was not found"));

        LocalDate checkoutDate = LocalDate.now();
        LocalDate returnDate = checkoutDate.plusDays(5L);

        LibraryTransaction libraryTransaction = new LibraryTransaction(checkoutDate, returnDate);
        libraryTransaction.setBook(book);
        libraryTransaction.setMember(member);
        libraryTransaction.setStaff(staff);
        libraryTransaction.setFineAmount(0d);

        return transactionRepository.save(libraryTransaction);

    }

    @Transactional
    public LibraryTransaction returnBook (Long transactionId){

        LibraryTransaction libraryTransaction = getTransactionById(transactionId);
        LocalDate actualReturnDate = LocalDate.now();
        libraryTransaction.setReturnDate(actualReturnDate);

        return transactionRepository.save(libraryTransaction);

    }

    public Double calculateFine (Long transactionId){

        LibraryTransaction libraryTransaction = getTransactionById(transactionId);
        LocalDate actualReturnDate = LocalDate.now();
        LocalDate checkoutDate = libraryTransaction.getCheckoutDate();

        long days = ChronoUnit.DAYS.between(checkoutDate, actualReturnDate);

        if (days > MAX_BOOK_DAYS){

            double fine = (double) 5 * (days - MAX_BOOK_DAYS);

            if (fine <= MAX_FINE)
                return fine;
            else
                return MAX_FINE;
        }
        else
            return 0d;

    }



}
