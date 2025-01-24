package com.example.spring.bzcustomerservice.dto;

import com.example.spring.bzcustomerservice.entity.Purchase;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseHistoryDTO {
    String orderId;
    String paymentKey;
    Long totalAmount;
    String approvedAt;
    String method;
    Long memberNo;
    String productList;

    public Purchase toPurchase() {
        return Purchase.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .totalAmount(totalAmount)
                .approvedAt(approvedAt)
                .method(method)
                .memberNo(memberNo)
                .productList(productList)
                .build();
    }

}
