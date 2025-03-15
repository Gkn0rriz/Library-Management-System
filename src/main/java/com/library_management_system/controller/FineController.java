package com.library_management_system.controller;

import com.library_management_system.entity.Fine;
import com.library_management_system.service.FineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping
    public ResponseEntity<Page<Fine>> getAllFines (@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Fine> fines = fineService.getAllFines(pageable);

        return ResponseEntity.ok(fines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFineById (@PathVariable Long id){

        Fine fine = fineService.getFineById(id);
        return ResponseEntity.ok(fine);
    }

    @GetMapping("/byMemberId")
    public ResponseEntity<Fine> getFineByMemberId (@RequestParam Long memberId){

        Fine fine = fineService.getFineByMemberId(memberId);
        return ResponseEntity.ok(fine);
    }

    @PostMapping
    public ResponseEntity<Fine> postFine (@RequestParam Long memberId,
                                          @RequestParam double fineAmount,
                                          @RequestParam String fineReason,
                                          @RequestParam String fineStatus){

        Fine fine = fineService.createFine(memberId, fineAmount, fineReason, fineStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(fine);
    }

    @PostMapping("/payFine/{fineId}")
    public ResponseEntity<String> postPayFine (@PathVariable Long fineId){

        fineService.payFine(fineId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Fine was paid successfully");

    }
}
