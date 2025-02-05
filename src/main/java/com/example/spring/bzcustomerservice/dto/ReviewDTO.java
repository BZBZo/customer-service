package com.example.spring.bzcustomerservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private Long reviewId;

    private String content;
    private LocalDateTime date;

    private Long memberNo;
    private Long productId;
    private Long purchaseId;

    private String imgUrls;
}
