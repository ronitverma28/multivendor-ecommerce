package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SellerResponse {
    private Long id;
    private String sellerName;
    private String mobileNumber;
    private String gstin;
    private String businessName;
    private String businessEmail;
    private String accountNumber;
    private String ifscCode;
    private String accountHolderName;
}
