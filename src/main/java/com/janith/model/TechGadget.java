package com.janith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechGadget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @ElementCollection
    private List<String> images;

    private String brand;
    private String specs;
    private boolean available;

    @ManyToOne
    private ComponentCategory category;

    public enum CompatibilityType {
        LAPTOP, PC, BOTH
    }

    @Enumerated(EnumType.STRING)
    private CompatibilityType compatibilityType;
}
