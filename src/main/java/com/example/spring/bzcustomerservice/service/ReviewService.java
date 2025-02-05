package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.ReviewDTO;
import com.example.spring.bzcustomerservice.entity.Review;
import com.example.spring.bzcustomerservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> findReviewsByPurchaseId(Long purchaseId) {
        return reviewRepository.findReviewsByPurchaseId(purchaseId);
    }

    public void saveReview(Long memberNo, Long productId, Long purchaseId, String content, String imgUrls) {
        log.info("📌 리뷰 저장 - memberNo: {}, productId: {}, purchaseId: {}, content: {}, imgUrls: {}",
                memberNo, productId, purchaseId, content, imgUrls);

        Review review = Review.builder()
                .memberNo(memberNo)
                .productId(productId)
                .purchaseId(purchaseId)
                .content(content)
                .date(LocalDateTime.now())
                .imgUrls(imgUrls)  // 쉼표(,)로 구분된 URL 저장
                .build();

        reviewRepository.save(review);

        log.info("✅ 리뷰 저장 완료 - ID: {}", review.getReviewId());
    }
}
