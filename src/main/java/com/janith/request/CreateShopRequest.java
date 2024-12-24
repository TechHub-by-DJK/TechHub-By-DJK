package com.janith.request;

import com.janith.model.Address;
import com.janith.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateShopRequest {
    private Long id;
    private String name;
    private String description;
    private String buildingtype;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
