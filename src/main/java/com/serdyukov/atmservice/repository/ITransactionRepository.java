package com.serdyukov.atmservice.repository;

import com.serdyukov.atmservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findById(String id);

    List<Transaction> findAllByCard_Id(Long id);

}
