package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.UserDetailsDto;
import com.flynn.crypttrade.dto.UserDto;
import com.flynn.crypttrade.facade.UserFacade;
import com.flynn.crypttrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getUserBalance(@PathVariable Long id) {
        BigDecimal balance = userService.getUserBalance(id);
        return ResponseEntity.ok(balance);
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.getUserDetails(id));
    }
}