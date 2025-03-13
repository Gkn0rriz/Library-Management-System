package com.library_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lab_ms_inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "INT default 0")
    private int quantityAvailable;

    private String shelfLocation;

    @Temporal(TemporalType.DATE)
    private Date acquisitionDate;

    @Column(columnDefinition = "double default 0")
    private Double inventoryCost;


    private String condition;

    @OneToOne
    private Book book;




}
