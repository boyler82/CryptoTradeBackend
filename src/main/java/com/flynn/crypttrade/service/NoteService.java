package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.Note;
import com.flynn.crypttrade.dto.NoteDto;
import com.flynn.crypttrade.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteDto create(NoteDto dto) {
        Note note = new Note();
        note.setUserId(dto.getUserId());
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setCreatedAt(LocalDateTime.now());
        return toDto(noteRepository.save(note));
    }

    public List<NoteDto> getAllForUser(Long userId) {
        return noteRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    public NoteDto update(Long id, NoteDto dto) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return toDto(noteRepository.save(note));
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    private NoteDto toDto(Note note) {
        return new NoteDto(note.getId(), note.getUserId(), note.getTitle(), note.getContent());
    }
}
