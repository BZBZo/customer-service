package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.PurchaseHistoryDTO;
import com.example.spring.bzcustomerservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/history")
    public void savePurchaseHistory(@RequestBody PurchaseHistoryDTO dto) {
        System.out.println("dto member No. :: " + dto.getMemberNo());
        try {
            purchaseService.savePurchaseHistory(dto);
            System.out.println("바로 구매 - 저장 성공");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
