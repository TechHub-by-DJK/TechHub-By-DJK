package com.janith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;

    @ManyToOne
    private Category computerCategory; //Gaming, Business, Student

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private boolean available;

    @ManyToOne
    private Shop shop;

    private boolean isHomeUser;
    private boolean isBusinessUser;
    private boolean isGamer;
    private boolean isDesigner;
    private boolean isDeveloper;
    private boolean isSeasonal;

    @ManyToMany
    private List<IncludedComponents> includedComponents = new ArrayList<>();

    private Date creationDate;
}
