package com.serdyukov.atmservice.repository;

import com.serdyukov.atmservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findById(Long cardId);

    boolean existsById(Long cardId);
}
