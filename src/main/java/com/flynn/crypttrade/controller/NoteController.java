package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.NoteDto;
import com.flynn.crypttrade.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> create(@RequestBody NoteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDto>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(noteService.getAllForUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> update(@PathVariable Long id, @RequestBody NoteDto dto) {
        return ResponseEntity.ok(noteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
