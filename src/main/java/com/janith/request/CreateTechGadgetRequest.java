package com.janith.request;

import lombok.Data;

@Data
public class CreateTechGadgetRequest {
    private String name;
    private String description;
    private Double price;
    private java.util.List<String> images;
    private String brand;
    private String specs;
    private boolean available;
    private Long categoryId;
    private Long shopId;
    private String compatibilityType; // LAPTOP, PC, BOTH
}

