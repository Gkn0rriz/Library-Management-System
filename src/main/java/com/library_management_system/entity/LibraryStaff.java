package com.library_management_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lab_ms_staffs")
public class LibraryStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String staffMemberName;

    @Column(name = "position")
    private String staffMemberPosition;

    @Column(name = "contact_number")
    private Long staffMemberContactNumber;

    @Column(name = "password")
    private String staffMemberPassword;

    @OneToMany (mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryTransaction> transactions;

}
