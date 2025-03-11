package com.library_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lab_ms_transactions")
public class LibraryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    private Long bookID;
    private Long userID;
    private LocalDate checkoutDate;
    private LocalDate returnDate;
    private Double fineAmount;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    @ManyToOne
    private LibraryStaff staff;

}
