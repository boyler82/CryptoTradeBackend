package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.UserDetailsDto;
import com.flynn.crypttrade.dto.UserDto;
import com.flynn.crypttrade.facade.UserFacade;
import com.flynn.crypttrade.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUser() {
        UserDto input = new UserDto();
        input.setEmail("test@test.com");
        UserDto output = new UserDto();
        output.setId(1L);
        when(userService.saveUser(input)).thenReturn(output);

        ResponseEntity<UserDto> response = userController.createUser(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldReturnAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(new UserDto()));
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldReturnUserById() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        when(userService.getUser(1L)).thenReturn(dto);
        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldDeleteUser() {
        ResponseEntity<Void> response = userController.deleteUser(1L);
        verify(userService).deleteUser(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnUserBalance() {
        when(userService.getUserBalance(1L)).thenReturn(BigDecimal.TEN);
        ResponseEntity<BigDecimal> response = userController.getUserBalance(1L);

        assertEquals(BigDecimal.TEN, response.getBody());
    }

    @Test
    void shouldReturnUserDetails() {
        UserDetailsDto dto = new UserDetailsDto();
        dto.setId(1L);
        when(userFacade.getUserDetails(1L)).thenReturn(dto);
        ResponseEntity<UserDetailsDto> response = userController.getUserDetails(1L);

        assertEquals(1L, response.getBody().getId());
    }
}