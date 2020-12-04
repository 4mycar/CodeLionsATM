package com.serdyukov.atmservice.controller;

import com.serdyukov.atmservice.dto.fromuser.UserAuthDto;
import com.serdyukov.atmservice.dto.fromuser.UserRegDto;
import com.serdyukov.atmservice.dto.touser.AuthResponse;
import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.exeption.AccessDeniedExeption;
import com.serdyukov.atmservice.security.JwtTokenProvider;
import com.serdyukov.atmservice.service.ICardService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "User authorize Controller")
@RequestMapping("/user")
public class CardController {

    @Autowired
    private ICardService usersService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${admin.key}")
    private String adminKey;

    @Operation(summary = "Create new user")
    @PostMapping(path = "/reg")
    ResponseEntity<Void> submitReg(@Valid @RequestBody UserRegDto userRegDto) {

        if (userRegDto != null && userRegDto.getAdminKey().equals(adminKey)) {
            usersService.addNewCard(userRegDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new AccessDeniedExeption("Your AdminKey is wrong");
        }

    }

    @Operation(summary = "Authorize existing user")
    @PostMapping(path = "/auth")
    ResponseEntity<AuthResponse> submitAuth(@Valid @RequestBody UserAuthDto userAuthDto) {
        if (userAuthDto != null) {
            Card card = usersService.findCardByIDAndPin(userAuthDto.getCardID(), userAuthDto.getPin());
            if (card != null) {
                String token = jwtTokenProvider.accessToken(card);
                return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}