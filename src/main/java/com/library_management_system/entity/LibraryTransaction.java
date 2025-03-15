package com.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name="lab_ms_transactions")
public class LibraryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDate checkoutDate;
    private LocalDate returnDate;
    private Double fineAmount;


    @ManyToOne
    @JoinColumn (name = "transaction_id", nullable = false)
    private LibraryStaff staff;

    public LibraryTransaction(LocalDate checkoutDate, LocalDate returnDate) {
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
    }
}
