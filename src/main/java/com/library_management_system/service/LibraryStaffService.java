package com.library_management_system.service;

import com.library_management_system.entity.LibraryStaff;
import com.library_management_system.repository.LibraryStaffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LibraryStaffService {

    private final LibraryStaffRepository staffRepository;

    private final ModelMapper modelMapper;


    public LibraryStaffService(LibraryStaffRepository staffRepository, ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    public Page<LibraryStaff> getAllLibraryStaff(Pageable pageable){

        return staffRepository.findAll(pageable);
    }

    public LibraryStaff getLibraryStaffById(Long id){

        return staffRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Staff member with id " + id + " was not found"));
    }

    public LibraryStaff searchStaffByName(String name){

        return staffRepository.findByStaffMemberName(name).orElseThrow(() ->
                new EntityNotFoundException("Staff member with name " + name + " was not found"));
    }

    public LibraryStaff addLibraryStaff(LibraryStaff staff){

        return staffRepository.save(staff);

    }

    public LibraryStaff updateLibraryStaff(Long id, LibraryStaff staff){

        if (staff == null)
            throw new IllegalStateException("Staff must not be null");

        LibraryStaff existingStaff = staffRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Staff member with id " + id + " was not found"));

        modelMapper.map(staff, existingStaff);

        return staffRepository.save(existingStaff);


    }

    public void deleteLibraryStaff(Long id){

        if (!staffRepository.existsById(id))
            throw new EntityNotFoundException("Staff member with id " + id + " was not found");

        staffRepository.deleteById(id);

    }
}
