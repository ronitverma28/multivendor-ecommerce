package com.ronit.ecommerce_multivendor.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerReportResponse {
    private Long totalEarnings;
    private Long totalSales;
    private Long totalRefunds;
    private Long totalTax;
    private Long netEarnings;
    private Integer totalOrders;
    private Integer canceledOrders;
    private Integer totalTransactions;
}
