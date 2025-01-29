package com.example.spring.bzcustomerservice.repository;

import com.example.spring.bzcustomerservice.dto.ReviewDTO;
import com.example.spring.bzcustomerservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<ReviewDTO> findReviewsByPurchaseId(Long purchaseId);
}
