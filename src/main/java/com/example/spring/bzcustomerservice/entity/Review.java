package com.example.spring.bzcustomerservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String content;
    private LocalDateTime date;

    private Long memberNo;
    private Long productId;
    private Long purchaseId;

    private String picturePath;

}
