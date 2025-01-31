package com.example.spring.bzcustomerservice.repository;

import com.example.spring.bzcustomerservice.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByMemberNo(Long memberNo);
}
