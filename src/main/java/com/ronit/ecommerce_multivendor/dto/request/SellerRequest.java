package com.ronit.ecommerce_multivendor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequest {
//  Personal Details
    private String sellerName;
    private String mobileNumber;
    private String gstin;

//  Business Details
    private String businessName;
    private String businessEmail;
    private String businessMobile;
    private String businessAddress;
    private String logo;
    private String banner;

//  Bank Details
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
}
