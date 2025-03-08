package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.PurchaseDTO;
import com.example.spring.bzcustomerservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/customer")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/history")
    public ResponseEntity<?> savePurchaseHistory(@RequestBody PurchaseDTO dto) {
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
    public Page<PurchaseDTO> getPurchaseListByMemberNo(
            @RequestParam Long memberNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return purchaseService.getPurchaseListByMemberNo(memberNo, page, size);
    }
}
