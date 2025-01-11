package com.janith.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long computerId;

    private int quantity;

    private List<String> includedComponents;
}
