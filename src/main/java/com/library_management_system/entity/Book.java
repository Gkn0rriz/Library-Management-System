package com.library_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lab_ms_books")
public class Book {
    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookName;

    private String bookAuthor;

    private Long bookISBN;

    private String bookGenre;

    @Temporal(TemporalType.DATE)
    private Date bookPublicationYear;

    private Boolean bookAvailabilityStatus;

    @OneToOne
    private Inventory inventory;

    @OneToMany
    private List<LibraryTransaction> libraryTransactions;

    @OneToMany
    private List<Reservation> reservations;
}
