package com.library_management_system.service;

import com.library_management_system.entity.Book;
import com.library_management_system.entity.Inventory;
import com.library_management_system.repository.BookRepository;
import com.library_management_system.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public InventoryService(InventoryRepository inventoryRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Inventory> getAllInventoryEntries (Pageable pageable){

        return inventoryRepository.findAll(pageable);
    }

    public Inventory getInventoryEntryById (long id){

        return inventoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Inventory entry with id " + id + " was not found"));
    }

    public Inventory getByBookId (long bookId){

        return inventoryRepository.findByBookId(bookId).orElseThrow(() ->
                new EntityNotFoundException("Inventory entry with id " + bookId + " was not found"));
    }

    public Inventory addInventoryEntry (long bookId, Inventory inventory){

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book not found"));
        inventory.setBook(book);

        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory (long id, Inventory inventory){

        Inventory existingInventory = getInventoryEntryById(id);
        modelMapper.map(inventory, existingInventory);

        return inventoryRepository.save(existingInventory);
    }

    public void deleteInventoryEntry (long id){

        inventoryRepository.deleteById(id);
    }


}
