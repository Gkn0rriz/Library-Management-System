package com.library_management_system.service;

import com.library_management_system.entity.Book;
import com.library_management_system.entity.Member;
import com.library_management_system.entity.Reservation;
import com.library_management_system.repository.BookRepository;
import com.library_management_system.repository.MemberRepository;
import com.library_management_system.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Reservation> getAllReservations (Pageable pageable){

        return reservationRepository.findAll(pageable);
    }

    public Reservation getReservationById (long id) {

        return reservationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Reservation with id " + id + " was not found"));
    }

    public Page<Reservation> getReservationsByMemberId (long memberId, Pageable pageable){

        return reservationRepository.findByMemberId(memberId, pageable);
    }

    public Reservation placeReservation (long memberId, long bookId){

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException("Member with id " + memberId + " was not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " was not found"));

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setMember(member);
        reservation.setReservationStatus(Reservation.RESERVED);
        reservation.setReservationDate(LocalDate.now());

        return reservationRepository.save(reservation);

    }

    public Reservation cancelReservation (long reservationId){

        Reservation reservation = getReservationById(reservationId);
        reservation.setReservationStatus(Reservation.FREE);

        return reservationRepository.save(reservation);
    }

}
