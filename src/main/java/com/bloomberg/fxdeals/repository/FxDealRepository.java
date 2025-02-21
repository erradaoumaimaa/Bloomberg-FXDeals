package com.bloomberg.fxdeals.repository;

import com.bloomberg.fxdeals.model.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, Long> {
    boolean existsByDealUniqueId(String dealUniqueId);
    Optional<FxDeal> findByDealUniqueId(String dealUniqueId);
}