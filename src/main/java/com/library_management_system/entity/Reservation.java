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
@Table(name = "lab_ms_reservations")
public class Reservation {

    public final static String RESERVED = "reserved";

    public final static String FREE = "free";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reservationDate;
    private String reservationStatus;

    @ManyToOne
    @JoinColumn (name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn (name = "book_id", nullable = false)
    private Book book;
}
