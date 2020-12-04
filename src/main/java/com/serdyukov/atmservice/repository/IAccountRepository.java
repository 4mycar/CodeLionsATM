package com.serdyukov.atmservice.repository;

import com.serdyukov.atmservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCard_Id(Long cardId);


}
