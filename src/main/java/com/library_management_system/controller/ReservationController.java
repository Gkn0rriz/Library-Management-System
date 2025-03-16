package com.library_management_system.controller;

import com.library_management_system.entity.Reservation;
import com.library_management_system.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<Page<Reservation>> getAllReservations (@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Reservation> reservationPage = reservationService.getAllReservations(pageable);

        return ResponseEntity.ok(reservationPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById (@PathVariable long id){

        Reservation reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<Reservation>> getByMemberId (@PathVariable long memberId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                            @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Reservation> reservationPage = reservationService.getReservationsByMemberId(memberId, pageable);

        return ResponseEntity.ok(reservationPage);

    }

    @PostMapping
    public ResponseEntity<Reservation> postReservation (@RequestParam long memberId,
                                                        @RequestParam long bookId) {

        Reservation reservation = reservationService.placeReservation(memberId, bookId);
        return ResponseEntity.ok(reservation);
    }


}
