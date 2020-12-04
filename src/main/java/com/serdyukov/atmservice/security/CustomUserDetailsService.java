package com.serdyukov.atmservice.security;

import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ICardRepository cardRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<Card> card = cardRepository.findById(Long.valueOf(id));
        return CustomUserDetails.fromUserEntityToCustomUserDetails(card.get());
    }
}
