package com.library_management_system.controller;

import com.library_management_system.entity.LibraryStaff;
import com.library_management_system.service.LibraryStaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/staff")
public class LibraryStaffController {

    private final LibraryStaffService staffService;

    public LibraryStaffController(LibraryStaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<Page<LibraryStaff>> getAllStaff (@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "name") String sortBy,
                                                           @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<LibraryStaff> staffPage = staffService.getAllLibraryStaff(pageable);
        return ResponseEntity.ok(staffPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryStaff> getStaffById (@PathVariable Long id){

        LibraryStaff staff = staffService.getLibraryStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/getByName")
    public ResponseEntity<LibraryStaff> getStaffByName (@RequestParam String name){

        LibraryStaff staff = staffService.searchStaffByName(name);
        return ResponseEntity.ok(staff);
    }

    @PostMapping
    public ResponseEntity<LibraryStaff> postStaff (@RequestBody LibraryStaff staff){

        LibraryStaff postedStaff = staffService.addLibraryStaff(staff);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postedStaff);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LibraryStaff> updateStaff (@PathVariable Long id, LibraryStaff staff){

        LibraryStaff updatedStaff = staffService.updateLibraryStaff(id, staff);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedStaff);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff (@PathVariable Long id){

        staffService.deleteLibraryStaff(id);

        return ResponseEntity
                .status(HttpStatus.OK).body("Staff member with id " + id + " has been successfully deleted");
    }


}
