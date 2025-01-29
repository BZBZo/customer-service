package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.ReviewDTO;
import com.example.spring.bzcustomerservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> findReviewsByPurchaseId(Long purchaseId) {
        return reviewRepository.findReviewsByPurchaseId(purchaseId);
    }
}
