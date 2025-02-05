package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.PurchaseDTO;
import com.example.spring.bzcustomerservice.dto.ReviewDTO;
import com.example.spring.bzcustomerservice.service.ImgServiceImpl;
import com.example.spring.bzcustomerservice.service.PurchaseService;
import com.example.spring.bzcustomerservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/customer")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final ReviewService reviewService;
    private final ImgServiceImpl imgServiceImpl;

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
    List<PurchaseDTO> getPurchaseListByMemberNo(@RequestParam Long memberNo){
        System.out.println("memberNo : " + memberNo);
        return purchaseService.getPurchaseListByMemberNo(memberNo);
    }

    @GetMapping("/history/review")
    List<ReviewDTO> findReviewsByPurchaseId(@RequestParam Long purchaseId){
        return reviewService.findReviewsByPurchaseId(purchaseId);
    }

    @PostMapping(value = "/history/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> writeReview(
            @RequestParam("memberNo") Long memberNo,  // 일반 텍스트 데이터는 @RequestParam으로 변경
            @RequestParam("productId") Long productId,
            @RequestParam("purchaseId") Long purchaseId,
            @RequestParam("content") String content,
            @RequestPart(value = "reviewImg", required = false) List<MultipartFile> images) {

        Map<String, String> response = new HashMap<>();

        try {
            log.info("📌 리뷰 저장 요청 - memberNo: {}, productId: {}, purchaseId: {}, content: {}",
                    memberNo, productId, purchaseId, content);

            // 이미지 URL 저장을 위한 String
            String imgUrls = "";

            // 이미지 업로드 (이미지가 있을 경우에만)
            if (images != null && !images.isEmpty()) {
                List<String> imgUrlList = new ArrayList<>();

                for (MultipartFile image : images) {
                    // S3에 업로드할 고유한 파일 이름 생성
                    String uniqueFileName = "static/bz-image/" + UUID.randomUUID();

                    // S3에 이미지 업로드 후 URL 반환
                    String imgUrl = imgServiceImpl.uploadImg(uniqueFileName, image);
                    imgUrlList.add(imgUrl);
                }

                // URL 리스트를 쉼표(,)로 연결하여 하나의 문자열로 변환
                imgUrls = String.join(",", imgUrlList);
            }

            log.info("📌 최종 저장할 이미지 URL: {}", imgUrls);

            // 리뷰 저장
            reviewService.saveReview(memberNo, productId, purchaseId, content, imgUrls);

            // 성공 응답
            response.put("success", "true");
            response.put("message", "리뷰 작성이 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("🚨 리뷰 저장 중 오류 발생: {}", e.getMessage(), e);

            // 예외 발생 시 에러 응답 반환
            response.put("success", "false");
            response.put("message", "리뷰 저장 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
