package com.janith.request;

import com.janith.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long shopId;
    private Address deliveryAddress;
}
