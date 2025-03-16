package com.library_management_system.controller;

import com.library_management_system.entity.Inventory;
import com.library_management_system.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<Page<Inventory>> getInventoryList (@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "id") String sortBy,
                                                             @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Inventory> inventoryPage = inventoryService.getAllInventoryEntries(pageable);

        return ResponseEntity.ok(inventoryPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryEntryById (@PathVariable long id){

        Inventory inventory = inventoryService.getInventoryEntryById(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Inventory> getByBookId (@PathVariable long bookId){

        Inventory inventory = inventoryService.getByBookId(bookId);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<Inventory> postInventoryEntry (@RequestParam long bookId,
                                                         @RequestBody Inventory inventory){

        Inventory postedInventory = inventoryService.addInventoryEntry(bookId, inventory);
        return ResponseEntity.ok(postedInventory);
    }

    @PutMapping("/updade/{id}")
    public ResponseEntity<Inventory> updateInventory (@PathVariable long id,
                                                      @RequestBody Inventory inventory){

        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        return ResponseEntity.ok(updatedInventory);
    }

}
