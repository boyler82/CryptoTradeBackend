package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.Note;
import com.flynn.crypttrade.dto.NoteDto;
import com.flynn.crypttrade.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note();
        note.setId(1L);
        note.setUserId(1L);
        note.setTitle("Test");
        note.setContent("Test Content");
        note.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateNote() {
        NoteDto dto = new NoteDto(null, 1L, "Test", "Test Content");
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteDto result = noteService.create(dto);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldGetAllNotesForUser() {
        when(noteRepository.findByUserId(1L)).thenReturn(List.of(note));

        List<NoteDto> notes = noteService.getAllForUser(1L);

        assertEquals(1, notes.size());
        assertEquals("Test", notes.get(0).getTitle());
    }

    @Test
    void shouldUpdateNote() {
        NoteDto updated = new NoteDto(1L, 1L, "Updated", "Updated Content");
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteDto result = noteService.update(1L, updated);

        assertEquals("Updated", result.getTitle());
        assertEquals("Updated Content", result.getContent());
    }

    @Test
    void shouldDeleteNote() {
        noteService.delete(1L);
        verify(noteRepository).deleteById(1L);
    }
}