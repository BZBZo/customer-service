package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.PurchaseHistoryDTO;
import com.example.spring.bzcustomerservice.entity.Purchase;
import com.example.spring.bzcustomerservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public void savePurchaseHistory(PurchaseHistoryDTO dto) {
        Purchase purchase = dto.toPurchase();
        purchaseRepository.save(purchase);
    }

    public List<PurchaseHistoryDTO> getPurchaseListByMemberNo(Long memberNo) {
        List<Purchase> purchases = purchaseRepository.findAllByMemberNo(memberNo);
        return purchases.stream()
                .map(purchase -> PurchaseHistoryDTO.builder()
                        .orderId(purchase.getOrderId())
                        .paymentKey(purchase.getPaymentKey())
                        .totalAmount(purchase.getTotalAmount())
                        .approvedAt(purchase.getApprovedAt())
                        .method(purchase.getMethod())
                        .memberNo(purchase.getMemberNo())
                        .productList(purchase.getProductList())
                        .build())
                .collect(Collectors.toList());
    }
}
