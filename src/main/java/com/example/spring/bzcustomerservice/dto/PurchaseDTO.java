package com.example.spring.bzcustomerservice.dto;

import com.example.spring.bzcustomerservice.entity.Purchase;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PurchaseDTO {
    String orderId;
    Long purchaseId;
    String paymentKey;
    Double totalAmount;
    String approvedAt;
    String method;
    Long memberNo;
    String productList;

    public Purchase toPurchase() {
        return Purchase.builder()
                .purchaseId(purchaseId)
                .orderId(orderId)
                .paymentKey(paymentKey)
                .totalAmount(totalAmount)
                .approvedAt(approvedAt)
                .method(method)
                .memberNo(memberNo)
                .productList(productList)
                .build();
    }

    @Transient
    private List<ProdReadResponseDTO> products;

}
