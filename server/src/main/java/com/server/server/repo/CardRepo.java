package com.server.server.repo;

import com.server.server.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, Long> {
    Card findByNumber(Long number);
}
