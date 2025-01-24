package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.PurchaseHistoryDTO;
import com.example.spring.bzcustomerservice.entity.Purchase;
import com.example.spring.bzcustomerservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public void savePurchaseHistory(PurchaseHistoryDTO dto) {
        Purchase purchase = dto.toPurchase();
        purchaseRepository.save(purchase);
    }
}
