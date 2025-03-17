package com.example.spring.bzcustomerservice.repository;

import com.example.spring.bzcustomerservice.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findAllByMemberNo(Long memberNo, Pageable pageable);
}
