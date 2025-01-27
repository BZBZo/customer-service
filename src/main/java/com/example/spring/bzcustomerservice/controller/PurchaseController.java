package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.PurchaseHistoryDTO;
import com.example.spring.bzcustomerservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/history")
    public ResponseEntity<?> savePurchaseHistory(@RequestBody PurchaseHistoryDTO dto) {
        System.out.println("dto member No. :: " + dto.getMemberNo());
        try {
            purchaseService.savePurchaseHistory(dto);
            System.out.println("구매 내역 저장 성공");
            return ResponseEntity.ok("저장 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청 데이터: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("저장 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러");
        }
    }

    @GetMapping("/history")
    List<PurchaseHistoryDTO> getPurchaseListByMemberNo(@RequestParam Long memberNo){
        return purchaseService.getPurchaseListByMemberNo(memberNo);
    }

}
