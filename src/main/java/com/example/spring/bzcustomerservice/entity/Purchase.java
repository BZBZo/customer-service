package com.example.spring.bzcustomerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId; //사실 필요없을것 같긴 하지만.. orderId를 id로 해도 되지만..내맘이야 룰루

    private String orderId;
    private String paymentKey;
    private Double totalAmount;
    private String approvedAt;
    private String method;
    private Long memberNo;
    private String productList;

}
