package com.janith.request;

import com.janith.model.Category;
import com.janith.model.IncludedComponents;
import com.janith.model.Shop;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreateComputerRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;
    private  Long shopId;

    private boolean isHomeUser;
    private boolean isBusinessUser;
    private boolean isGamer;
    private boolean isDesigner;
    private boolean isDeveloper;
    private boolean isSeasonal;

    private List<IncludedComponents> includedComponents;

    private String brand;
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String operatingSystem;
    private Double rating;
    private Integer stockQuantity;
    private String computerType; // LAPTOP or PC

}
