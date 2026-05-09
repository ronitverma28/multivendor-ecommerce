package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressSnapshotResponse {
    private String name;
    private String mobile;
    private String locality;
    private String address;
    private String city;
    private String state;
    private String pinCode;
}
