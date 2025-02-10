package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.PurchaseDTO;
import com.example.spring.bzcustomerservice.dto.ReviewDTO;
import com.example.spring.bzcustomerservice.service.ImgServiceImpl;
import com.example.spring.bzcustomerservice.service.PurchaseService;
import com.example.spring.bzcustomerservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<PurchaseDTO> getPurchaseListByMemberNo(
            @RequestParam Long memberNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return purchaseService.getPurchaseListByMemberNo(memberNo, page, size);
    }

    @GetMapping("/history/review")
    List<ReviewDTO> findReviewsByPurchaseId(@RequestParam Long purchaseId) {
        return reviewService.findReviewsByPurchaseId(purchaseId);
    }

    @GetMapping("/product/review/list")
    Page<ReviewDTO> getReviewList(@RequestParam Long productId,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestHeader("Accept") String acceptHeader // Accept 헤더 추가
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("reviewId").ascending());
        return reviewService.findReviewsByProductId(productId, pageable);
    }

    @GetMapping("/product/review/count")
    Integer countReview(@RequestParam Long productId){
        return reviewService.countReview(productId).intValue();
    }


    @PostMapping(value = "/history/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> writeReview(
            @RequestParam("memberNo") Long memberNo,
            @RequestParam("productId") Long productId,
            @RequestParam("purchaseId") Long purchaseId,
            @RequestParam("content") String content,
            @RequestPart(value = "reviewImg", required = false) MultipartFile[] images) {

        Map<String, String> response = new HashMap<>();

        try {
            log.info("📌 리뷰 저장 요청 - memberNo: {}, productId: {}, purchaseId: {}, content: {}",
                    memberNo, productId, purchaseId, content);

            String imgUrls = "";

            if (images != null && images.length > 0) {
                List<String> imgUrlList = new ArrayList<>();

                for (MultipartFile image : images) {
                    if (image == null || image.isEmpty()) {
                        // 빈 파일은 건너뜁니다.
                        continue;
                    }
                    String uniqueFileName = "static/bz-image/" + UUID.randomUUID();
                    String imgUrl = imgServiceImpl.uploadImg(uniqueFileName, image);
                    imgUrlList.add(imgUrl);
                }

                imgUrls = String.join(",", imgUrlList);
            }

            log.info("📌 최종 저장할 이미지 URL: {}", imgUrls);

            reviewService.saveReview(memberNo, productId, purchaseId, content, imgUrls);

            response.put("success", "true");
            response.put("message", "리뷰 작성이 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("🚨 리뷰 저장 중 오류 발생: {}", e.getMessage(), e);
            response.put("success", "false");
            response.put("message", "리뷰 저장 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/history/review/detail")
    ReviewDTO findReviewByIds(
            @RequestParam("purchaseId") Long purchaseId,
            @RequestParam("productId") Long productId,
            @RequestParam("memberNo") Long memberNo) {
        return reviewService.findReviewByIds(purchaseId, productId, memberNo);
    }


}
